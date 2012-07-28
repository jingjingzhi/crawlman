package webcrawl.run;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import webcrawl.rule.Rule;
import webcrawl.rule.accept.HostNameAcceptRule;
import webcrawl.rule.accept.PathAcceptMatchRule;
import webcrawl.rule.accept.RequestDepthRule;
import webcrawl.rule.reject.HandlerRejectRule;
import webcrawl.run.CrawlJob;


public class TestCrawlJob {

	@Test
	public void testReject() {
		CrawlJob crawlJob = new CrawlJob("www.china.alibaba.com");
		crawlJob.putConf(CrawlJob.HANDLER_REJECT_RULE_KEY, "do|js|css");

		crawlJob.putConf(CrawlJob.HANDLER_REJECT_RULE_KEY + "=do|js|css");
		List<Rule> rejctRules = crawlJob.getRejectRules();
		Assert.assertEquals(1, rejctRules.size());
		Assert.assertEquals(true,
				rejctRules.get(0).getClass() == HandlerRejectRule.class);

		crawlJob.putConf(CrawlJob.HANDLER_REJECT_RULE_KEY + "=xdo|js|css");
		rejctRules = crawlJob.getRejectRules();
		Assert.assertEquals(2, rejctRules.size());

		crawlJob.putConf(CrawlJob.HOST_NAME_REJCT_RULE_KEY
				+ "=www.china.alibaba.com");
		rejctRules = crawlJob.getRejectRules();
		Assert.assertEquals(3, rejctRules.size());

		crawlJob.putConf(CrawlJob.PATH_REJECT_RULE_KEY + "=/sdfasd/aa.*");
		rejctRules = crawlJob.getRejectRules();
		Assert.assertEquals(4, rejctRules.size());

		// wrong regex pattern which will not be accepted
		crawlJob.putConf(CrawlJob.PATH_REJECT_RULE_KEY + "=/sdfasd/aa[");
		rejctRules = crawlJob.getRejectRules();
		Assert.assertEquals(4, rejctRules.size());

	}

	@Test
	public void testAccept() {
		CrawlJob crawlJob = new CrawlJob("www.china.alibaba.com");
		crawlJob.putConf(CrawlJob.HOST_NAME_ACCEPT_RULE_KEY,
				"www.china.alibaba.com");

		List<Rule> acceptRules = crawlJob.getAcceptRules();
		Assert.assertEquals(1, acceptRules.size());
		Assert.assertEquals(true,
				acceptRules.get(0).getClass() == HostNameAcceptRule.class);

		crawlJob.putConf(CrawlJob.PATH_ACCEPT_RULE_KEY, "/sdfasd/aa.*");
		acceptRules = crawlJob.getAcceptRules();
		Assert.assertEquals(2, acceptRules.size());
		Assert.assertEquals(true,
				acceptRules.get(1).getClass() == PathAcceptMatchRule.class);

		// wrong regex pattern which will not be accepted
		crawlJob.putConf(CrawlJob.PATH_ACCEPT_RULE_KEY, "/sdfasd/aa.*[");
		acceptRules = crawlJob.getAcceptRules();
		Assert.assertEquals(2, acceptRules.size());
		Assert.assertEquals(true,
				acceptRules.get(1).getClass() == PathAcceptMatchRule.class);

		crawlJob.putConf(CrawlJob.REQUEST_DEPTH_ACCEPT_RULE_KEY, "5");
		acceptRules = crawlJob.getAcceptRules();
		Assert.assertEquals(3, acceptRules.size());
		// the order of the RequestPathRule is the lowest, so it will be in the
		// first after sorting
		Assert.assertEquals(5,
				((RequestDepthRule) acceptRules.get(0)).getDepthLimit());
	}

}
