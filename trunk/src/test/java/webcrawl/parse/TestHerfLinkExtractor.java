package webcrawl.parse;

import junit.framework.Assert;

import org.junit.Test;

import webcrawl.parse.HerfLinkExtractor;

public class TestHerfLinkExtractor  {
	
	HerfLinkExtractor e = new HerfLinkExtractor();
	
	@Test
	public void testGetLink(){
		
		
		Assert.assertEquals("http://www.cogoo.net", e
						.extract("<!DOCTYPE html><body><a href=\"http://www.cogoo.net\">ddd</a><div></body></html>").toArray()[0]);
		
	}
	
	@Test
	public void test2(){
		e.extract(this.getClass().getClassLoader().getResourceAsStream("com/alibaba/spu/webcrawl/parse/tested.html"));
	}

}
