package webcrawl.request;

import junit.framework.Assert;

import org.junit.Test;

import webcrawl.request.HttpRequest;
import webcrawl.rule.accept.PathAcceptMatchRule;
import webcrawl.rule.target.TargetPathMatchRule;
import webcrawl.run.CrawlException;


public class TestPathAcceptMatchRule {

	@Test
	public void test() {
		try {
			TargetPathMatchRule targetMatch = new TargetPathMatchRule(
					"/mall/.*|/mall");

			Assert.assertEquals(true, targetMatch.match(new HttpRequest(
					"http://www.cogoo.net/mall/list.php?catid=41", 3)));
			Assert.assertEquals(true, targetMatch.match(new HttpRequest(
					"http://www.cogoo.net/mall", 3)));
			Assert.assertEquals(true, targetMatch.match(new HttpRequest(
					"http://www.cogoo.net/mall/", 3)));

		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testAccept() {
		try {
			TargetPathMatchRule targetMatch = new TargetPathMatchRule(
					"/mall.*|/sell.*");

			Assert.assertEquals(false, targetMatch.match(new HttpRequest(
					"http://www.cogoo.net/about/xieyi.html", 3)));
			Assert.assertEquals(false, targetMatch.match(new HttpRequest(
					"http://www.cogoo.net/feed/", 3)));
			Assert.assertEquals(
					false,
					targetMatch
							.match(new HttpRequest(
									"http://wpa.qq.com/msgrd?v=3&uin=34455630&site=qq&menu=yes",
									3)));

			

		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testAccept2() {
		try {
			PathAcceptMatchRule targetMatch = new PathAcceptMatchRule(
					"/mall.*|/sell.*");
			Assert.assertEquals(
					false,
					targetMatch
							.match(new HttpRequest(
									"http://www.cogoo.net/index.php?homepage=1021449209&file=credit&view=1",
									3)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
