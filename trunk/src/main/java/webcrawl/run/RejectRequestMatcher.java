package webcrawl.run;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.request.HttpRequest;
import webcrawl.rule.Rule;


public class RejectRequestMatcher implements RequestMatcher {
	private static Log log = LogFactory.getLog(RejectRequestMatcher.class);

	public RejectRequestMatcher(List<Rule> rules) {
		this.rules = rules;
	}

	private List<Rule> rules;

	public boolean match(HttpRequest request) {

		if (request.isSeed()) {
			return false;
		}

		for (Rule rule : this.rules) {
			if (rule.match(request)) {
				if (log.isDebugEnabled()) {
					log.debug(" match" + rule + ", url:" + request.getUrl());
				}
				return true;
			}
		}

		return false;
	}

}
