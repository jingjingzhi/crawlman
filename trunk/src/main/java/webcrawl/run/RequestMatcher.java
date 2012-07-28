package webcrawl.run;

import webcrawl.request.HttpRequest;

public interface RequestMatcher {
	boolean match(HttpRequest request);
}
