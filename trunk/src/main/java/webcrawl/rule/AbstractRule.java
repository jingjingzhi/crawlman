package webcrawl.rule;

import webcrawl.rule.regex.RegexMatcher;
import webcrawl.run.CrawlException;


public abstract class AbstractRule implements Rule {

	public AbstractRule(String pattern) throws CrawlException {
		if (pattern == null || "".equals(pattern.trim())) {
			throw new CrawlException("No pattern provided.");
		}
		this.matcher = new RegexMatcher(pattern.trim());
	}

	public AbstractRule() {

	}

	protected Matcher matcher;

	protected int order;
	protected String id;

	public int compareTo(Rule arg0) {
		return this.order - arg0.order();
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}

	public int order() {
		return order;
	}

	public String id() {
		return id;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "[" + this.type() + "], rule:" + this.matcher.getMatchRule();
	}

}
