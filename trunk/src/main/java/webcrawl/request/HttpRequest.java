package webcrawl.request;

import java.net.MalformedURLException;
import java.net.URL;

import webcrawl.util.DigestUtil;


/**
 * take a url for example:
 * http://china.alibaba.com/offer/detail.htm?offerId=1341
 * 
 * @author jingjing.zhijj
 * 
 */
public class HttpRequest {

	private String url;
	private String protocol;
	private String host;
	private String path;// without scheme, host and parameter. like
						// /offer/detail.htm
	private String query;

	private String handler;

	private int depth;
	private int responseCode;
	/**
	 * has this request been posted by the http client.
	 */
	private boolean issued;

	private boolean seed;

	private String digest;

	public HttpRequest(String url, int depth) throws MalformedURLException {
		this.url = url;
		this.depth = depth + 1;

		URL httpurl = new URL(url);
		this.protocol = httpurl.getProtocol();
		this.host = httpurl.getHost();
		this.path = httpurl.getPath();
		this.query = httpurl.getQuery();

		if (path != null) {
			String[] pathSeps = path.split("\\.");
			if (pathSeps.length == 2) {
				this.handler = pathSeps[1];
			}
		}

		this.digest = DigestUtil.digest(url);

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	@Override
	public String toString() {
		return this.url;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HttpRequest) {
			HttpRequest r = (HttpRequest) obj;
			return url.equalsIgnoreCase(r.getUrl());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.url.hashCode();
	}

	public boolean isIssued() {
		return issued;
	}

	public void setIssued(boolean issued) {
		this.issued = issued;
	}

	public boolean isSeed() {
		return seed;
	}

	public void setSeed(boolean seed) {
		this.seed = seed;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

}
