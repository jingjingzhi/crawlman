package webcrawl.run;

import java.net.MalformedURLException;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.parse.Extractor;
import webcrawl.parse.HerfLinkExtractor;
import webcrawl.request.HttpRequest;
import webcrawl.request.HttpResult;
import webcrawl.request.PostRequester;
import webcrawl.run.event.CrawlJobEvent;
import webcrawl.run.event.EventBus;


/**
 * 
 * @author jingjing.zhijj
 * 
 */
public class HttpRequestTask implements Runnable {

	private static Log log = LogFactory.getLog(HttpRequestTask.class);

	private PostRequester requester = new PostRequester();

	private HttpRequest httpRequest;

	private RequestBus requestBus;

	private EventBus eventBus;

	private Extractor<Set<String>> extractor = new HerfLinkExtractor();

	public void run() {
		HttpResult result = requester.request(httpRequest.getUrl());
		httpRequest.setIssued(true);
		eventBus.publishEvent(new CrawlJobEvent(
				CrawlJobEvent.CRAWL_TASK_FINISHED, httpRequest));
		if (result.isRequestSucceeded()) {
			if (log.isDebugEnabled()) {
				log.debug(httpRequest.getUrl() + " requested.");
			}
			Set<String> urls = extractor.extract(result.getResponse());
			if (urls != null) {
				for (String url : urls) {
					if (url == null) {
						continue;
					}
					try {
						String[] urla = url.split("#");
						HttpRequest r = new HttpRequest(urla[0],
								httpRequest.getDepth());
						this.requestBus.submit(r);

					} catch (MalformedURLException e) {
					}
				}
			}
		} else {
			if (log.isWarnEnabled()) {
				log.warn(httpRequest.getUrl() + " failed.");
			}
		}
	}

	public PostRequester getRequester() {
		return requester;
	}

	public void setRequester(PostRequester requester) {
		this.requester = requester;
	}

	public HttpRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public RequestBus getRequestBus() {
		return requestBus;
	}

	public void setRequestBus(RequestBus requestBus) {
		this.requestBus = requestBus;
	}

	public Extractor<Set<String>> getExtractor() {
		return extractor;
	}

	public void setExtractor(Extractor<Set<String>> extractor) {
		this.extractor = extractor;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

}
