package webcrawl.request;

import java.net.MalformedURLException;

import junit.framework.Assert;

import org.junit.Test;

import webcrawl.request.HttpRequest;
import webcrawl.request.MemoryRequestGuardar;

public class TestMemoryRequestContainer {
	@Test
	public void test() throws MalformedURLException {
		MemoryRequestGuardar container = new MemoryRequestGuardar();
		
		String url = "http://www.cogoo.net/mall/search.php?areaid=12&catid=304";
		container
				.add(new HttpRequest(url, 3));
		
		
		Assert.assertEquals(true, container.contains(new HttpRequest(url, 3)));
		
		
		System.out.println(Runtime.getRuntime().totalMemory());
		for(int i = 0 ;i < 2000000;i ++){
			url = "http://www.cogoo.net/mall/search.php?areaid=12&catid="+i;
			container
			.add(new HttpRequest(url, 3));
		}
		
		System.out.println(Runtime.getRuntime().totalMemory());
	}

}
