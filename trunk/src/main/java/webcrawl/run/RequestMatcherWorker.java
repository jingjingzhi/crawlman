package webcrawl.run;

import java.util.List;

import webcrawl.request.HttpRequest;
import webcrawl.rule.Rule;


/**
 * 
 * @author jingjing.zhijj
 * 
 */
public class RequestMatcherWorker {

	public RequestMatcherWorker() {

	}

	public RequestMatcherWorker(List<Rule> rejectRules, List<Rule> acceptRules) {
		if (rejectRules != null) {
			this.rejectMatcher = new RejectRequestMatcher(rejectRules);
		}
		if (acceptRules != null) {
			this.acceptMatcher = new AcceptRequestMatcher(acceptRules);
		}
	}

	private RequestMatcher rejectMatcher;
	private RequestMatcher acceptMatcher;

	public boolean isRejected(HttpRequest request) {

		if (this.rejectMatcher.match(request)) {
			return true;
		}

		// if there is one accept rule, and the request does not matched the
		// rule, then the request will be rejected
		if (!this.acceptMatcher.match(request)) {
			return true;
		}

		return false;
	}

}
