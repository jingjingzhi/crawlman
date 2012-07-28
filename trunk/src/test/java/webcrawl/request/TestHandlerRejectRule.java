package webcrawl.request;

import junit.framework.Assert;

import org.junit.Test;

import webcrawl.request.HttpRequest;
import webcrawl.rule.reject.HandlerRejectRule;


public class TestHandlerRejectRule {

	@Test
	public void test() {
		try {
			HandlerRejectRule targetMatch = new HandlerRejectRule(
					"js|css|do|aspx");

			Assert.assertEquals(false, targetMatch.match(new HttpRequest(
					"http://www.cogoo.net/sell/show.php?itemid=41", 3)));
			Assert.assertEquals(true, targetMatch.match(new HttpRequest(
					"http://www.cogoo.net/sell/show.js?itemid=41", 3)));

		} catch (Exception e) {
			Assert.fail();
		}
	}

}
