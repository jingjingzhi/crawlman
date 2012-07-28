package webcrawl.request;

/**
 * Container to hold all the http requests that have been discovered and may not
 * be processed.
 * 
 * @author jingjing.zhijj
 * 
 */
public interface HttpRequestGuardar {

	boolean contains(HttpRequest request);

	void add(HttpRequest request);

}
