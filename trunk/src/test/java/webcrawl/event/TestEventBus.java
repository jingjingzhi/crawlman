package webcrawl.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import webcrawl.flow.FlowMeasureBucket;
import webcrawl.request.HttpRequest;
import webcrawl.request.MemoryRequestGuardar;
import webcrawl.run.CrawlJobRunner;
import webcrawl.run.FlowMonitor;
import webcrawl.run.HttpRequestTaskExecutorEngine;
import webcrawl.run.JobProgressChecker;
import webcrawl.run.RequestBus;
import webcrawl.run.event.CrawlJobEvent;
import webcrawl.run.event.EventBus;


public class TestEventBus {

	private static Log log = LogFactory.getLog(TestEventBus.class);

	private EventBus eventBus = new EventBus();

	private void init() {

		RequestBus requestBus = new RequestBus();

		JobProgressChecker jobProgressChecker = new JobProgressChecker();
		jobProgressChecker.setEventBus(eventBus);
		jobProgressChecker.setRequestBus(requestBus);
		eventBus.addListener(new JobProgressChecker());

		MemoryRequestGuardar memoryRequestGuardar = new MemoryRequestGuardar();
		eventBus.addListener(memoryRequestGuardar);

		FlowMonitor flowMonitor = new FlowMonitor();
		flowMonitor.setFlowMeasureBucket(new FlowMeasureBucket());
		HttpRequestTaskExecutorEngine engine = new HttpRequestTaskExecutorEngine();
		CrawlJobRunner CrawlJobRunner = new CrawlJobRunner();
		CrawlJobRunner.setTaskExecutorEngine(engine);
		flowMonitor.setJobRunner(CrawlJobRunner);

		eventBus.addListener(flowMonitor);
	}

	@Test
	public void test() throws Exception {
		this.init();
		eventBus.startRunnerThread();
		eventBus.publishEvent(new CrawlJobEvent(
				CrawlJobEvent.CRAWL_JOB_STARTED, ""));
		eventBus.publishEvent(new CrawlJobEvent(
				CrawlJobEvent.CRAWL_TASK_SUBMITTED, new HttpRequest("http://www.1688.com/123442341", 3)));
		eventBus.publishEvent(new CrawlJobEvent(
				CrawlJobEvent.CRAWL_TASK_FINISHED, new HttpRequest("http://www.1688.com", 3)));

		try {
			Thread.sleep(100000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
