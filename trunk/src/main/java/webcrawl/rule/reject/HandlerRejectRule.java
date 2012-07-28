package webcrawl.rule.reject;

import webcrawl.request.HttpRequest;
import webcrawl.rule.AbstractRule;
import webcrawl.rule.Rule;
import webcrawl.run.CrawlException;


public class HandlerRejectRule extends AbstractRule {

	public HandlerRejectRule(String pattern) throws CrawlException {
		super(pattern);
		this.order = 0;
	}

	/**
	 * if matched, then rejected
	 */
	public boolean match(HttpRequest request) {
		return matcher.matches(request.getHandler());
	}

	public String type() {
		return Rule.REJECT_TYPE;
	}

}
