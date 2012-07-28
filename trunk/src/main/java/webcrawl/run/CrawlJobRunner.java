package webcrawl.run;

import java.net.MalformedURLException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.flow.FlowMeasureBucket;
import webcrawl.request.HttpRequest;
import webcrawl.request.MemoryRequestGuardar;
import webcrawl.run.event.CrawlJobEvent;
import webcrawl.run.event.EventBus;


/**
 * difficulties: 1) how to determine the HTTP request flow in the last second?
 * 2) how to determine the job is finished? 3) how to make sure the HTTP
 * response's character encoding? 4) how to adjust the HTTP request flow? 5) how
 * to rule out the unwanted URL? 6) how to avoid endless recursive HTTP requests
 * in pages? 7) how to get the job resume working from the point where it was
 * interrupted?
 * 
 * @author jingjing.zhijj
 * 
 */
public class CrawlJobRunner extends Thread implements JobRunner {
	private static Log log = LogFactory.getLog(CrawlJobRunner.class);

	private boolean instantiated;
	private boolean paused;
	private boolean need2Stop;

	/**
	 * will be injected by spring bean
	 */
	private RequestBus requestBus;

	/**
	 * will be injected by spring bean
	 */
	private FlowMeasureBucket flowMeasureBucket;

	/**
	 * will be injected by spring bean
	 */
	private HttpRequestTaskExecutorEngine taskExecutorEngine;
	/**
	 * will be injected by spring bean
	 */
	private HttpRequestTaskExecutorEngine targetExecutorEngine;

	public void setTargetExecutorEngine(
			HttpRequestTaskExecutorEngine targetExecutorEngine) {
		this.targetExecutorEngine = targetExecutorEngine;
	}

	/**
	 * will be injected by spring bean
	 */
	private EventBus eventBus;

	private MemoryRequestGuardar memoryRequestGuardar;

	private JobProgressChecker jobProgressChecker;

	private Job job;

	private ReentrantLock lock = new ReentrantLock();
	private Condition pauseCondition = lock.newCondition();

	private FlowMonitor flowMonitor;

	public void pauseJob() {
		paused = true;
	}

	public void shutdownJob() {
		// TODO first pause the job
		// TODO then persist the job
		// TODO exit current thread
		this.need2Stop = true;

	}

	public void resumeJob() {
		lock.lock();
		try {
			this.pauseCondition.signalAll();
		} finally {
			lock.unlock();
		}
		this.paused = false;

		if (log.isInfoEnabled()) {
			log.info("job resumed.");
		}

	}

	public void instantiate() throws CrawlException {

		if (this.job == null) {
			throw new CrawlException("Job detail can not be null.");
		}

		memoryRequestGuardar = new MemoryRequestGuardar();

		this.requestBus.setFlowMeasureBucket(flowMeasureBucket);
		RequestMatcherWorker requestMatcherWorker = new RequestMatcherWorker(
				this.job.getRejectRules(), this.job.getAcceptRules());
		this.requestBus.setRequestMatcherWorker(requestMatcherWorker);
		this.requestBus.setTargetMatcherWorker(new TargetMatcherWorker(this.job
				.getTargetRules(), this.targetExecutorEngine));
		this.requestBus.setHttpRequestGuardar(memoryRequestGuardar);
		this.requestBus.setEventBus(this.eventBus);

		this.flowMonitor = new FlowMonitor();
		this.flowMonitor.setFlowMeasureBucket(flowMeasureBucket);
		this.flowMonitor.setJobRunner(this);

		this.jobProgressChecker = new JobProgressChecker();
		this.jobProgressChecker.setEventBus(this.eventBus);
		this.jobProgressChecker.setRequestBus(requestBus);

		// initialized event listeners
		this.eventBus.addListener(this.jobProgressChecker);
		this.eventBus.addListener(this.memoryRequestGuardar);
		this.eventBus.addListener(this.flowMonitor);
		this.eventBus.addListener(this.taskExecutorEngine);
		this.eventBus.addListener(this.targetExecutorEngine);
		//event bus should be placed last, because the bus have to be shutdown last
		this.eventBus.addListener(this.eventBus);
		
		this.eventBus.startRunnerThread();

		this.instantiated = true;
	}

	public void startJob() throws CrawlException {
		if (!instantiated) {
			throw new CrawlException("not instantiated.");
		}

		try {
			HttpRequest seed = new HttpRequest(job.getSeed(), 1);
			seed.setSeed(true);
			if (log.isInfoEnabled()) {
				log.info("start crawling with seed: " + seed);
			}
			requestBus.submit(seed);
		} catch (MalformedURLException e) {
			throw new CrawlException("Failed to get seed: " + job.getSeed(), e);
		}

		this.start();

		this.eventBus.publishEvent(new CrawlJobEvent(
				CrawlJobEvent.CRAWL_JOB_STARTED, "Job with sedd ["
						+ this.job.getSeed() + "] started."));

	}

	@Override
	public void run() {
		HttpRequest request = null;
		while (true) {
			if (this.need2Stop) {
				break;
			}
			if (this.paused) {
				if (log.isInfoEnabled()) {
					log.info("job will be paused.");
				}
				pauseAwait();
			}

			request = this.requestBus.take();

			if (request != null) {
				HttpRequestTask task = new HttpRequestTask();
				task.setRequestBus(this.requestBus);
				task.setHttpRequest(request);
				task.setEventBus(this.eventBus);
				taskExecutorEngine.execute(task);
			}

		}
	}

	private void pauseAwait() {
		lock.lock();
		try {
			this.pauseCondition.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void slowRunner() {
		this.taskExecutorEngine.shrink();
	}

	public void speedupRunner() {
		this.taskExecutorEngine.expand();
	}

	public RequestBus getRequestBus() {
		return requestBus;
	}

	public void setRequestBus(RequestBus requestBus) {
		this.requestBus = requestBus;
	}

	public FlowMeasureBucket getFlowMeasureBucket() {
		return flowMeasureBucket;
	}

	public void setFlowMeasureBucket(FlowMeasureBucket flowMeasureBucket) {
		this.flowMeasureBucket = flowMeasureBucket;
	}

	public void setTaskExecutorEngine(
			HttpRequestTaskExecutorEngine taskExecutorEngine) {
		this.taskExecutorEngine = taskExecutorEngine;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isNeed2Stop() {
		return need2Stop;
	}

	public void setNeed2Stop(boolean need2Stop) {
		this.need2Stop = need2Stop;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public void setMemoryRequestGuardar(
			MemoryRequestGuardar memoryRequestGuardar) {
		this.memoryRequestGuardar = memoryRequestGuardar;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

}
