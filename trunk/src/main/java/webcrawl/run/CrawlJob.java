package webcrawl.run;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.rule.Rule;
import webcrawl.rule.accept.HostNameAcceptRule;
import webcrawl.rule.accept.PathAcceptMatchRule;
import webcrawl.rule.accept.RequestDepthRule;
import webcrawl.rule.reject.HandlerRejectRule;
import webcrawl.rule.reject.PathRejectMatchRule;
import webcrawl.rule.target.TargetPathMatchRule;

import com.google.common.base.Splitter;
import com.google.common.collect.LinkedHashMultimap;

/**
 * a crawl job setting
 * 
 * @author jingjing.zhijj
 * 
 */
public class CrawlJob implements Job {

	private static Log log = LogFactory.getLog(CrawlJob.class);

	/**
	 * this is the crawl starting point
	 */
	private String seed;

	private LinkedHashMultimap<String, String> configuration = LinkedHashMultimap
			.create();

	private List<Rule> rejectRules = Collections.emptyList();
	private List<Rule> acceptRules = Collections.emptyList();
	private List<Rule> targetRules = Collections.emptyList();

	public static String HOST_NAME_REJCT_RULE_KEY = "reject_rule.host_name";
	public static String HANDLER_REJECT_RULE_KEY = "reject_rule.handler";
	public static String PATH_REJECT_RULE_KEY = "reject_rule.path";

	public static String HOST_NAME_ACCEPT_RULE_KEY = "accept_rule.host_name";
	public static String PATH_ACCEPT_RULE_KEY = "accept_rule.path";
	public static String REQUEST_DEPTH_ACCEPT_RULE_KEY = "accept_rule.request_depth";

	public static String TARGET_RULE_KEY = "target_rule.path";

	public String getSeed() {
		return this.seed;
	}

	public CrawlJob(String seed) {
		this.seed = seed;
	}

	@Override
	public List<Rule> getRejectRules() {

		this.rejectRules = parseRejctRules();

		return this.rejectRules;
	}

	private List<Rule> parseRejctRules() {
		List<Rule> rules = new ArrayList<Rule>();

		addRules(HostNameAcceptRule.class, HOST_NAME_REJCT_RULE_KEY, rules);
		addRules(HandlerRejectRule.class, HANDLER_REJECT_RULE_KEY, rules);
		addRules(PathRejectMatchRule.class, PATH_REJECT_RULE_KEY, rules);

		Collections.sort(rules);
		return rules;
	}

	@Override
	public List<Rule> getAcceptRules() {

		this.acceptRules = parseAcceptRules();

		return this.acceptRules;
	}

	private List<Rule> parseAcceptRules() {
		List<Rule> rules = new ArrayList<Rule>();

		addRules(HostNameAcceptRule.class, HOST_NAME_ACCEPT_RULE_KEY, rules);
		addRules(PathAcceptMatchRule.class, PATH_ACCEPT_RULE_KEY, rules);

		Set<String> ruleList = this.configuration
				.get(REQUEST_DEPTH_ACCEPT_RULE_KEY);
		if (ruleList.size() > 0) {
			String depth = (String) ruleList.toArray()[0];
			int depthInt = 5;
			try {
				depthInt = Integer.parseInt(depth);
			} catch (NumberFormatException e) {
				if (log.isWarnEnabled()) {
					log.warn(REQUEST_DEPTH_ACCEPT_RULE_KEY
							+ " rule: wrong value, using default depth: "
							+ depthInt);
				}
			}

			RequestDepthRule requestDepthRule = new RequestDepthRule(depthInt);
			rules.add(requestDepthRule);
		}

		Collections.sort(rules);
		return rules;
	}

	@Override
	public List<Rule> getTargetRules() {

		List<Rule> rules = new ArrayList<Rule>();
		addRules(TargetPathMatchRule.class, TARGET_RULE_KEY, rules);

		Collections.sort(rules);

		this.targetRules = rules;

		return this.targetRules;
	}

	private void addRules(Class<? extends Rule> ruleClz, String key,
			List<Rule> rules) {

		Set<String> ruleList = this.configuration.get(key);

		Constructor<? extends Rule> constructor = null;
		try {
			constructor = ruleClz.getConstructor(String.class);
		} catch (Exception e1) {
			if (log.isErrorEnabled()) {
				log.error("No appropriate constructor available.");
			}
		}

		if (constructor == null) {
			return;
		}

		for (String rule : ruleList) {

			Rule ruleObject = newRuleInstance(constructor, rule);
			if (ruleObject != null) {
				rules.add(ruleObject);
			} else {
				if (log.isWarnEnabled()) {
					log.warn(key + "=" + rule
							+ " can not be recognized as a regex pattern.");
				}
			}

		}
	}

	private Rule newRuleInstance(Constructor<? extends Rule> constructor,
			String pattern) {
		try {

			return constructor.newInstance(pattern);
		} catch (Exception e1) {
			log.error("can not new an instance of " + constructor
					+ " using parameter: " + pattern);
		}
		return null;
	}

	public void putConf(String key, String value) {
		Set<String> values = this.configuration.get(key);
		values.add(value);
		configuration.putAll(key, values);
	}

	/**
	 * configuration source like reject_rule.host_name=www.cogoo.net
	 * 
	 * @param config
	 */
	public void putConf(List<String> configLines) {
		if (configLines != null) {
			for (String config : configLines) {

				this.putConf(config);

			}
		}
	}

	/**
	 * configuration source like reject_rule.host_name=www.cogoo.net
	 * 
	 * @param config
	 */
	public void putConf(String config) {

		if (config == null) {
			return;
		}

		config = config.trim();
		if ("".equals(config) || config.startsWith("#")) {
			return;
		}

		Iterable<String> kvi = Splitter.on('=').omitEmptyStrings()
				.split(config);

		Iterator<String> iter = kvi.iterator();
		if (iter.hasNext()) {
			String k = iter.next();
			if (iter.hasNext()) {
				String v = iter.next();

				this.putConf(k, v);
			} else {
				if (log.isWarnEnabled()) {
					log.warn("configuration: key [" + k
							+ "] has no value, will be ignored.");
				}
			}
		} else {
			if (log.isWarnEnabled()) {
				log.warn("configuration: ["
						+ config
						+ "] is not a recognized format like [k=v], will be ignored.");
			}
		}
	}

}
