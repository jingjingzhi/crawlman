package webcrawl.rule.reject;

import webcrawl.request.HttpRequest;
import webcrawl.rule.AbstractRule;
import webcrawl.rule.Rule;
import webcrawl.run.CrawlException;


public class PathRejectMatchRule extends AbstractRule {

	public PathRejectMatchRule(String pattern) throws CrawlException {
		super(pattern);
		this.order = 1;
	}

	public boolean match(HttpRequest request) {
		return matcher.matches(request.getPath());
	}

	public String type() {
		return Rule.REJECT_TYPE;
	}

}
