package webcrawl.run;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.request.HttpRequest;
import webcrawl.request.HttpResult;
import webcrawl.request.PostRequester;


public class TargetTask implements Runnable {

	private static Log log = LogFactory.getLog(TargetTask.class);

	private HttpRequest request;
	private PostRequester postRequester = new PostRequester();

	public TargetTask(HttpRequest request) {
		this.request = request;
	}

	public void run() {
		HttpResult result = this.postRequester.request(this.request.getUrl());
		this.request.setIssued(true);
		if (result.isRequestSucceeded()) {
			// TODO save to file
			if(log.isInfoEnabled()){
				log.info("receive target: " + this.request.getUrl());
			}
		}
	}

}
