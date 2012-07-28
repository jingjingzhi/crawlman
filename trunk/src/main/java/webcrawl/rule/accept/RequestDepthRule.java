package webcrawl.rule.accept;

import webcrawl.request.HttpRequest;
import webcrawl.rule.AbstractRule;
import webcrawl.rule.Rule;


public class RequestDepthRule extends AbstractRule {
	// default to 3
	private int depthLimit = 3;

	public RequestDepthRule(int depthLimit) {
		this.depthLimit= depthLimit;
		this.order = 2;
	}

	public boolean match(HttpRequest request) {
		return request.getDepth() < this.depthLimit;
	}

	public int getDepthLimit() {
		return depthLimit;
	}

	public void setDepthLimit(int depthLimit) {
		this.depthLimit = depthLimit;
	}

	public String type() {
		return Rule.ACCEPT_TYPE;
	}

}
