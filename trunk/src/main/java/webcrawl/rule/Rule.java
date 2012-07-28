package webcrawl.rule;

import webcrawl.request.HttpRequest;

/**
 * check whether the request is accepted
 * 
 * @author jingjing.zhijj
 * 
 */
public interface Rule extends Comparable<Rule> {
	int order();

	String id();

	boolean match(HttpRequest request);

	/**
	 * three type: accept, reject, target
	 * 
	 * @return
	 */
	String type();
	
	String ACCEPT_TYPE = "accept";
	String REJECT_TYPE = "reject";
	String TARGET_TYPE = "target";
}
