package webcrawl.run;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.flow.FlowMeasureBucket;
import webcrawl.run.event.AbstractCrawlJobEventListener;


public class FlowMonitor extends AbstractCrawlJobEventListener {

	private static Log log = LogFactory.getLog(FlowMonitor.class);

	private long sleepTime = 1000l;
	private long upperLimitedFlow = 200;
	private long lowerLimitedFlow = 20;

	private FlowChecker flowChecker;

	private FlowMeasureBucket flowMeasureBucket;
	private CrawlJobRunner jobRunner;

	private boolean stop;

	public void check() {
		if (log.isInfoEnabled()) {
			log.info("FlowMonitor start to check...");
		}
		while (!stop) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long flow = this.flowMeasureBucket.getFlow();
			if (log.isDebugEnabled()) {
				log.debug("flow is :" + flow);
			}
			if (flow > this.upperLimitedFlow) {
				if (log.isDebugEnabled()) {
					log.debug("slowing down job runner, overflowing limit:"
							+ this.upperLimitedFlow);
				}

				this.jobRunner.slowRunner();
			} else if (flow < this.lowerLimitedFlow) {
				if (log.isDebugEnabled()) {
					log.debug("speeding up job runner, lower limit:"
							+ this.lowerLimitedFlow);
				}
				this.jobRunner.speedupRunner();
			}
		}
		
		if (log.isInfoEnabled()) {
			log.info("stop flow monitoring on job finished.");
		}
	}

	@Override
	protected void onJobStarted() {
		startChecker();
	}

	private void startChecker() {
		flowChecker = new FlowChecker();
		flowChecker.setDaemon(true);
		flowChecker.start();
	}

	@Override
	protected void onJobInterrupted() {
		this.stop = true;
	}

	@Override
	protected void onJobResumed() {
		startChecker();
	}

	@Override
	protected void onJobFinished() {
		this.stop = true;
	}
	

	class FlowChecker extends Thread {

		@Override
		public void run() {
			check();
		}

	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public FlowMeasureBucket getFlowMeasureBucket() {
		return flowMeasureBucket;
	}

	public void setFlowMeasureBucket(FlowMeasureBucket flowMeasureBucket) {
		this.flowMeasureBucket = flowMeasureBucket;
	}

	public CrawlJobRunner getJobRunner() {
		return jobRunner;
	}

	public void setJobRunner(CrawlJobRunner jobRunner) {
		this.jobRunner = jobRunner;
	}
}
