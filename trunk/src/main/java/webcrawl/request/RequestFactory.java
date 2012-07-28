package webcrawl.request;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import webcrawl.parse.Extractor;
import webcrawl.parse.HerfLinkExtractor;


public class RequestFactory {

	private MemoryRequestGuardar container = new MemoryRequestGuardar();
	private PostRequester postRequester = new PostRequester();
	private Extractor<Set<String>> extractor= new HerfLinkExtractor();

	public Set<HttpRequest> create(HttpRequest request) {
		if(container.contains(request)){
			return Collections.emptySet();
		}
		Set<HttpRequest> requests = new LinkedHashSet<HttpRequest>();
		HttpResult result = postRequester.request(request.getUrl());
		container.add(request);
		if (result != null && result.isRequestSucceeded()) {
			Set<String> urls = extractor.extract(result.getResponse());
			if (urls != null) {
				for(String url : urls){
					try {
						HttpRequest r= new HttpRequest(url,request.getDepth());
						requests.add(r);
					} catch (MalformedURLException e) {
//						System.out.println("malformed url :"+url);
					}
				}
			}
		}
		return requests;
	}

}
