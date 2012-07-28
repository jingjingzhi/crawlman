package webcrawl.run;

import java.util.List;

import webcrawl.rule.Rule;


/**
 * a crawl job
 * 
 * @author jingjing.zhijj
 * 
 */
public interface Job {
	/**
	 * the original site to be used as a seed to start crawling
	 * 
	 * @return
	 */
	String getSeed();

	/**
	 * a list of rules that will reject incoming requests
	 * 
	 * @return
	 */
	List<Rule> getRejectRules();

	/**
	 * a list of rules that will accept incoming requests
	 * 
	 * @return
	 */
	List<Rule> getAcceptRules();

	/**
	 * a list of rules that will match incoming request as a target request
	 * 
	 * @return
	 */
	List<Rule> getTargetRules();

}
