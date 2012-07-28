package webcrawl.run;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.request.HttpRequest;
import webcrawl.run.event.AbstractCrawlJobEventListener;
import webcrawl.run.event.CrawlJobEvent;
import webcrawl.run.event.EventBus;


/**
 * 
 * @author jingjing.zhijj
 * 
 */
public class JobProgressChecker extends AbstractCrawlJobEventListener {
	private static Log log = LogFactory.getLog(JobProgressChecker.class);
	private RequestBus requestBus;
	private JobState jobState = new JobState();
	private EventBus eventBus;
	private boolean stop;

	private CheckerThread checkerThread;

	public JobProgressChecker() {

	}

	private void check() {
		if(log.isInfoEnabled()){
			log.info("JobProgressChecker start to check...");
		}
		while (!stop) {
			
			long finished = this.jobState.getFinished();
			long submitted = this.jobState.getSubmited();
			long remaining = submitted - finished;

			if (log.isDebugEnabled()) {

				log.debug("finished: " + finished + ", submitted:" + submitted
						+ ", remaining: " + remaining);
			}

			if (remaining== 0 && this.requestBus.isEmpty()) {
				if (log.isInfoEnabled()) {
					log.info("Job finished");
				}

				eventBus.publishEvent(new CrawlJobEvent(
						CrawlJobEvent.CRAWL_JOB_FINISHED, "Job finished."));
				break;
			}
			
			sleep();

		}
	}

	private void sleep() {
		try {
			Thread.sleep(15000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	class CheckerThread extends Thread {

		@Override
		public void run() {
			check();
		}

	}

	@Override
	protected void onJobStarted() {
		this.checkerThread = new CheckerThread();
		this.checkerThread.setDaemon(true);
		this.checkerThread.start();
	}

	@Override
	protected void onJobInterrupted() {
		this.stop = true;
	}

	@Override
	protected void onJobFinished() {
		this.stop = true;
		if (log.isInfoEnabled()) {
			log.info("stop checking  on job finished.");
		}
	}

	@Override
	protected void onTaskSubmitted(HttpRequest request) {
		this.jobState.addSubmitted();
	}

	@Override
	protected void onTaskFinished(HttpRequest request) {
		this.jobState.addFinished();
	}

	public RequestBus getRequestBus() {
		return requestBus;
	}

	public void setRequestBus(RequestBus requestBus) {
		this.requestBus = requestBus;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

}
