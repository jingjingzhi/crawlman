package webcrawl.rule;

public class RuleContants {
	/**
	 * may conflict with HOST_HOP_RULE
	 */
	public static String HOST_NAME_RULE = "rule.host_name";
	/**
	 * can we crawl outside of the seed host
	 */
	public static String HOST_HOP_RULE = "rule.host_hop";// default to false

}
