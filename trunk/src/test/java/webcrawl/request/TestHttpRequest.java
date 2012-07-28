package webcrawl.request;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;

import webcrawl.request.HttpRequest;

public class TestHttpRequest {
	
	@Test
	public void test(){
		try {
			HttpRequest request = new HttpRequest("http://search.china.alibaba.com/selloffer/-D2E5CEDACBDCC1CF-1031639.html?from=industrySearch&industryFlag=material", 0);
			Assert.assertEquals("http", request.getProtocol());
			Assert.assertEquals("search.china.alibaba.com", request.getHost());
			Assert.assertEquals("/selloffer/-D2E5CEDACBDCC1CF-1031639.html", request.getPath());
			Assert.assertEquals("from=industrySearch&industryFlag=material", request.getQuery());
			Assert.assertEquals("html", request.getHandler());
		} catch (MalformedURLException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testhandler2(){
		try {
			HttpRequest request = new HttpRequest("http://search.china.alibaba.com/selloffer/", 0);
			Assert.assertEquals(null, request.getHandler());
		} catch (MalformedURLException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testFailed(){
		try {
			HttpRequest request = new HttpRequest("http//search.china.alibaba.com/selloffer/-D2E5CEDACBDCC1CF-1031639.html?from=industrySearch&industryFlag=material", 0);
			Assert.fail();
		} catch (MalformedURLException e) {
			
		}
	}

}
