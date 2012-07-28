package webcrawl.request;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.run.JobState;
import webcrawl.run.event.AbstractCrawlJobEventListener;


public class MemoryRequestGuardar extends AbstractCrawlJobEventListener
		implements HttpRequestGuardar {

	private static Log log = LogFactory.getLog(MemoryRequestGuardar.class);

	// use this structure to save memory space
	private Set<String> requests = Collections
			.synchronizedSet(new HashSet<String>());

	private JobState jobState = new JobState();

	public boolean contains(HttpRequest request) {
		return this.requests.contains(request.getDigest());
	}

	@Override
	public void add(HttpRequest request) {
		this.requests.add(request.getDigest());
	}

	@Override
	protected void onJobInterrupted() {
		// TODO
		throw new RuntimeException("not implemented!");
	}

	@Override
	protected void onJobFinished() {
		log.info("submit:" + this.jobState.getSubmited() + ", finished:"
				+ this.jobState.getFinished());
		this.requests.clear();
		if (log.isInfoEnabled()) {
			log.info("memory clear on job finished.");
		}
	}

	@Override
	protected void onTaskSubmitted(HttpRequest request) {
		this.requests.add(request.getDigest());
	}

}
