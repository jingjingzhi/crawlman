package webcrawl.rule.accept;

import webcrawl.request.HttpRequest;
import webcrawl.rule.AbstractRule;
import webcrawl.rule.Rule;
import webcrawl.run.CrawlException;


public class PathAcceptMatchRule extends AbstractRule {
	public PathAcceptMatchRule(String pattern) throws CrawlException {
		super(pattern);
		this.order = 4;
	}

	public boolean match(HttpRequest request) {
		return matcher.matches(request.getPath());
	}

	public String type() {
		return Rule.ACCEPT_TYPE;
	}
}
