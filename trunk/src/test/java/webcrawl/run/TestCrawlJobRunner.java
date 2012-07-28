package webcrawl.run;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import webcrawl.run.CrawlException;
import webcrawl.run.CrawlJob;
import webcrawl.run.CrawlJobRunner;

public class TestCrawlJobRunner {

	@Test
	public void testRun() throws CrawlException {

		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "classpath:/crawl.xml" });
		CrawlJobRunner crawlJobRunner = (CrawlJobRunner) appContext
				.getBean("crawlJobRunner");
		Assert.assertNotNull(crawlJobRunner);

		CrawlJob job = new CrawlJob("http://www.cogoo.net");
		// accept multiple reject rules
		job.putConf(CrawlJob.HANDLER_REJECT_RULE_KEY, "css|js|do");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/buy.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/company.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/invest.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/group.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/quote.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/exhibit.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/brand.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/job.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/know.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/special.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/photo.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/video.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/down.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/archiver.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/extend.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/wap.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/spread.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/member.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/news.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/sell/search.php.*");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/mall/search.php.*");

		// accept one accept path rule
		job.putConf(CrawlJob.HOST_NAME_ACCEPT_RULE_KEY, "www.cogoo.net");
		job.putConf(CrawlJob.PATH_ACCEPT_RULE_KEY, "/mall.*|/sell.*");

		job.putConf(CrawlJob.TARGET_RULE_KEY
				+ "=/mall/show.php.*|/sell/show.php.*");

		crawlJobRunner.setJob(job);
		crawlJobRunner.instantiate();
		crawlJobRunner.startJob();

		try {
			Thread.sleep(1000000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void testSample() throws CrawlException {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "classpath:/crawl.xml" });
		CrawlJobRunner crawlJobRunner = (CrawlJobRunner) appContext
				.getBean("crawlJobRunner");
		Assert.assertNotNull(crawlJobRunner);

		CrawlJob job = new CrawlJob("http://www.1688.com");
		// accept multiple reject rules
		job.putConf(CrawlJob.HANDLER_REJECT_RULE_KEY, "css|js|do");
		job.putConf(CrawlJob.PATH_REJECT_RULE_KEY, "/archiver.*");
		job.putConf(CrawlJob.HOST_NAME_ACCEPT_RULE_KEY, "www.1688.com|www.china.alibaba.com");
		job.putConf(CrawlJob.PATH_ACCEPT_RULE_KEY, "/mall.*|/sell.*");

		job.putConf(CrawlJob.TARGET_RULE_KEY
				+ "=/mall/detail.htm.*|/sell/detail.htm.*");

		crawlJobRunner.setJob(job);
		crawlJobRunner.instantiate();
		crawlJobRunner.startJob();
	}

}
