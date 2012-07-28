package webcrawl.request;

import org.apache.commons.httpclient.HttpStatus;

public class HttpResult {

	private String url;
	private int statuCode = -1;
	private String response;

	public String getUri() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setStatuCode(int statuCode) {
		this.statuCode = statuCode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	public boolean isRequestMade(){
		return this.statuCode != -1;
	}
	
	public boolean isRequestSucceeded(){
		return this.statuCode == HttpStatus.SC_OK;
	}

}
