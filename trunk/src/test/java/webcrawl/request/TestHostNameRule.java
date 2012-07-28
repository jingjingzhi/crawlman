package webcrawl.request;

import junit.framework.Assert;

import org.junit.Test;

import webcrawl.request.HttpRequest;
import webcrawl.rule.accept.HostNameAcceptRule;


public class TestHostNameRule {

	@Test
	public void test() {
		try {
			HostNameAcceptRule HostNameRule = new HostNameAcceptRule(
					"www.cogoo.net");

			Assert.assertEquals(true, HostNameRule.match(new HttpRequest(
					"http://www.cogoo.net/sell/show.php?itemid=41", 3)));

		} catch (Exception e) {
			Assert.fail();
		}
	}

}
