package webcrawl.run;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.flow.FlowMeasureBucket;
import webcrawl.request.HttpRequest;
import webcrawl.request.HttpRequestGuardar;
import webcrawl.run.event.CrawlJobEvent;
import webcrawl.run.event.EventBus;


/**
 * 
 * @author jingjing.zhijj
 * 
 */
public class RequestBus {

	private static Log log = LogFactory.getLog(RequestBus.class);

	private FlowMeasureBucket flowMeasureBucket = new FlowMeasureBucket();

	private Queue<HttpRequest> requestQueue = new LinkedBlockingQueue<HttpRequest>();

	private HttpRequestGuardar httpRequestGuardar;

	private RequestMatcherWorker requestMatcherWorker;
	private TargetMatcherWorker targetMatcherWorker;

	private EventBus eventBus;

	public void submit(HttpRequest request) {
		if (httpRequestGuardar.contains(request)) {
			if (log.isDebugEnabled()) {
				log.debug(request.getUrl() + " already been requested.");
			}
			return;
		}
		// process rules
		if (this.requestMatcherWorker.isRejected(request)) {
			return;
		}

		// if it's matched, it means the request has been handled.
		// and there is no need to proceed.
		if (this.targetMatcherWorker.match(request)) {
			this.httpRequestGuardar.add(request);
			return;
		}

		if (log.isInfoEnabled()) {
			log.info(request.getUrl() + " added to request queue.");
		}
		this.requestQueue.add(request);
		this.httpRequestGuardar.add(request);
		this.flowMeasureBucket.add();
		eventBus.publishEvent(new CrawlJobEvent(
				CrawlJobEvent.CRAWL_TASK_SUBMITTED, request));

	}

	public HttpRequest take() {

		HttpRequest request = this.requestQueue.poll();

		return request;
	}

	public Queue<HttpRequest> getRequestQueue() {
		return requestQueue;
	}

	public boolean isEmpty() {
		return this.requestQueue.isEmpty();
	}

	public void setRequestQueue(Queue<HttpRequest> requestQueue) {
		this.requestQueue = requestQueue;
	}

	public void setHttpRequestGuardar(HttpRequestGuardar httpRequestGuardar) {
		this.httpRequestGuardar = httpRequestGuardar;
	}

	public FlowMeasureBucket getFlowMeasureBucket() {
		return flowMeasureBucket;
	}

	public void setFlowMeasureBucket(FlowMeasureBucket flowMeasureBucket) {
		this.flowMeasureBucket = flowMeasureBucket;
	}

	public RequestMatcherWorker getRequestMatcherWorker() {
		return requestMatcherWorker;
	}

	public void setRequestMatcherWorker(
			RequestMatcherWorker requestMatcherWorker) {
		this.requestMatcherWorker = requestMatcherWorker;
	}

	public TargetMatcherWorker getTargetMatcherWorker() {
		return targetMatcherWorker;
	}

	public void setTargetMatcherWorker(TargetMatcherWorker targetMatcherWorker) {
		this.targetMatcherWorker = targetMatcherWorker;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

}
