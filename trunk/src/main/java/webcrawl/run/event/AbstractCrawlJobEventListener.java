package webcrawl.run.event;

import webcrawl.request.HttpRequest;

public abstract class AbstractCrawlJobEventListener implements EventListener {

	@Override
	public void onEvent(Event event) {

		if (CrawlJobEvent.interested(CrawlJobEvent.CRAWL_JOB_STARTED,
				event.getType())) {
			this.onJobStarted();
		}
		if (CrawlJobEvent.interested(CrawlJobEvent.CRAWL_JOB_INTERRUPTED,
				event.getType())) {
			this.onJobInterrupted();
		}
		if (CrawlJobEvent.interested(CrawlJobEvent.CRAWL_JOB_RESUMED,
				event.getType())) {
			this.onJobResumed();
		}
		if (CrawlJobEvent.interested(CrawlJobEvent.CRAWL_JOB_FINISHED,
				event.getType())) {
			this.onJobFinished();
		}

		if (CrawlJobEvent.interested(CrawlJobEvent.CRAWL_TASK_SUBMITTED,
				event.getType())) {
			if (event.getSource() instanceof HttpRequest) {
				HttpRequest request = (HttpRequest) event.getSource();
				this.onTaskSubmitted(request);
			}
		}
		if (CrawlJobEvent.interested(CrawlJobEvent.CRAWL_TASK_FINISHED,
				event.getType())) {
			if (event.getSource() instanceof HttpRequest) {
				HttpRequest request = (HttpRequest) event.getSource();
				this.onTaskFinished(request);
			}
		}

	}

	protected void onJobStarted() {
	}

	protected void onJobInterrupted() {
	}

	protected void onJobResumed() {
	}

	protected void onJobFinished() {
	}

	protected void onTaskSubmitted(HttpRequest request) {
	}

	protected void onTaskTaken(HttpRequest request) {
	}

	protected void onTaskFinished(HttpRequest request) {
	}

}
