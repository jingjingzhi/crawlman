package webcrawl.request;

import junit.framework.Assert;

import org.junit.Test;

import webcrawl.request.HttpRequest;
import webcrawl.rule.target.TargetPathMatchRule;


public class TestTargetPathMatchRule {

	@Test
	public void test() {
		try {
			TargetPathMatchRule targetMatch = new TargetPathMatchRule(
					"/sell/show.php\\\\?.*");

			Assert.assertEquals(false, targetMatch.match(new HttpRequest(
					"http://www.cogoo.net/mall/show.php?itemid=4", 3)));

		} catch (Exception e) {
			Assert.fail();
		}
	}

}
