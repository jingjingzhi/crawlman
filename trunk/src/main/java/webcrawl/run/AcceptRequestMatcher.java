package webcrawl.run;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.request.HttpRequest;
import webcrawl.rule.Rule;
import webcrawl.rule.accept.PathAcceptMatchRule;


public class AcceptRequestMatcher implements RequestMatcher {

	private static Log log = LogFactory.getLog(AcceptRequestMatcher.class);

	public AcceptRequestMatcher(List<Rule> rules) {
		this.rules = rules;
	}

	private List<Rule> rules;

	public boolean match(HttpRequest request) {

		if (request.isSeed()) {
			return true;
		}

		for (Rule rule : this.rules) {
			
			if(rule instanceof PathAcceptMatchRule && rule.match(request)){
				if (log.isDebugEnabled()) {
					log.debug(" match" + rule + ", url:" + request.getUrl());
				}
				return true;
			}
			
			if (!rule.match(request)) {
				if (log.isDebugEnabled()) {
					log.debug("not match" + rule + ", url:" + request.getUrl());
				}
				return false;
			}
		}

		return false;
	}

}
