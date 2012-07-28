package webcrawl.rule.accept;

import webcrawl.request.HttpRequest;
import webcrawl.rule.AbstractRule;
import webcrawl.rule.Rule;
import webcrawl.run.CrawlException;


public class HostNameAcceptRule extends AbstractRule {

	public HostNameAcceptRule(String pattern) throws CrawlException {
		super(pattern);
		this.order = 3;
	}

	public boolean match(HttpRequest request) {
		return matcher.matches(request.getHost());
	}

	public String type() {
		return Rule.ACCEPT_TYPE;
	}

}
