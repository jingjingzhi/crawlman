package webcrawl.request;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author jingjing.zhijj
 * 
 */
public class PostRequester {

	private static Log log = LogFactory.getLog(PostRequester.class);

	private static String CONTENT_CHARSET = "UTF-8";

	private HttpClient client = new HttpClient();
	{
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				CONTENT_CHARSET);
	}

	public HttpResult request(String url) {

		HttpResult result = new HttpResult();
		result.setUrl(url);

		HttpMethod method = new GetMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));
		method.getParams().setParameter("http.socket.timeout", new Integer(15000));
		method.setRequestHeader("Content-Type", "text/html;charset=UTF-8");

		try {
			client.executeMethod(method);

			result.setStatuCode(method.getStatusCode());

			result.setResponse(method.getResponseBodyAsString());

		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(url + " failed.", e);
			}
		}

		return result;

	}

}
