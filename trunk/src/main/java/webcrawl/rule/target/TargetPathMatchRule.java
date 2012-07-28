package webcrawl.rule.target;

import webcrawl.request.HttpRequest;
import webcrawl.rule.AbstractRule;
import webcrawl.rule.Rule;
import webcrawl.run.CrawlException;


public class TargetPathMatchRule extends AbstractRule {
	public TargetPathMatchRule(String pattern) throws CrawlException {
		super(pattern);
		this.order = 101;
	}

	public boolean match(HttpRequest request) {
		return matcher.matches(request.getPath());
	}
	
	public String type() {
		return Rule.TARGET_TYPE;
	}

}