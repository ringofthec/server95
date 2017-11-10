package loginserver.utils.qihoo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.AccessControlException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
/**
 * A utility class to handle HTTP request/response.
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class HttpClient implements java.io.Serializable {
	private static final int OK = 200;// OK: Success!
    private static final int NOT_MODIFIED = 304;// Not Modified: There was no new data to return.
    private static final int BAD_REQUEST = 400;// Bad Request: The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.
    private static final int NOT_AUTHORIZED = 401;// Not Authorized: Authentication credentials were missing or incorrect.
    private static final int FORBIDDEN = 403;// Forbidden: The request is understood, but it has been refused.  An accompanying error message will explain why.
    private static final int NOT_FOUND = 404;// Not Found: The URI requested is invalid or the resource requested, such as a user, does not exists.
    private static final int NOT_ACCEPTABLE = 406;// Not Acceptable: Returned by the Search API when an invalid format is specified in the request.
    private static final int INTERNAL_SERVER_ERROR = 500;// Internal Server Error: Something is broken.  Please post to the group so the QihooSDK team can investigate.
    private static final int BAD_GATEWAY = 502;// Bad Gateway: QihooSDK is down or being upgraded.
    private static final int SERVICE_UNAVAILABLE = 503;// Service Unavailable: The QihooSDK servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.

    private int retryCount = 3;
	private int retryIntervalMillis = 10000;
	private int connectionTimeout = 20000;
	private int readTimeout = 120000;
	private static final long serialVersionUID = 1127440347711522033L;
	private static final boolean DEBUG = true;
	private static boolean isJDK14orEarlier = false;
	private Map<String, String> requestHeaders = new HashMap<String, String>();

	static {
		try {
			String versionStr = System
					.getProperty("java.specification.version");
			if (null != versionStr) {
				isJDK14orEarlier = 1.5d > Double.parseDouble(versionStr);
			}
		} catch (AccessControlException ace) {
			isJDK14orEarlier = true;
		}
	}

	public HttpClient() {
		setRequestHeader("Accept-Encoding", "gzip");
	}

	public String get(String url, PostParameter[] params)
			throws QihooException, NoSuchAlgorithmException,
			KeyManagementException {
		if (url.indexOf("?") == -1) {
			url += "?";
		}
		if (null != params && params.length > 0) {
			url += HttpClient.encodeParameters(params);
		}
		log(url);
		return httpRequest(url, null, "GET").asString();
	}

	public Response post(String url, PostParameter[] postParameters)
			throws QihooException, NoSuchAlgorithmException,
			KeyManagementException {
		return httpRequest(url, postParameters, "POST");
	}

	public Response delete(String url) throws QihooException,
			NoSuchAlgorithmException, KeyManagementException {
		return httpRequest(url, null, "DELETE");
	}

	public Response httpRequest(String url, PostParameter[] postParams,String httpMethod) {
		try
		{
			OutputStream osw = null;
			HttpsURLConnection con = getConnection(url);
			con.setDoInput(true);
			setHeaders(url, postParams, con, httpMethod);
			if ("GET".equals(httpMethod)) {
				con.setRequestMethod("GET");

			} else if ("POST".equals(httpMethod)) {
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				con.setDoOutput(true);
				String postParam = "";
				if (postParams != null)
					postParam = encodeParameters(postParams);
				log("Post Params: ", postParam);
				byte[] bytes = postParam.getBytes("utf-8");
				con.setRequestProperty("Content-Length", Integer
						.toString(bytes.length));
				osw = con.getOutputStream();
				osw.write(bytes);
				osw.flush();
				osw.close();
			} else if ("DELETE".equals(httpMethod)) {
				con.setRequestMethod("DELETE");
			}
			return new Response(con);
		}
		catch (Exception ex)
		{
			log("httpRequest is error = " + ex.toString());
		}
		return null;
	}

	public static String encodeParameters(PostParameter[] postParams) {
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < postParams.length; j++) {
			if (j != 0) {
				buf.append("&");
			}
			try {
				buf
						.append(URLEncoder.encode(postParams[j].name, "utf-8"))
						.append("=")
						.append(URLEncoder.encode(postParams[j].value, "utf-8"));
			} catch (java.io.UnsupportedEncodingException neverHappen) {
			}
		}
		return buf.toString();

	}

	/**
	 * sets HTTP headers
	 * 
	 * @param connection
	 *            HttpURLConnection
	 * @param authenticated
	 *            boolean
	 */
	private void setHeaders(String url, PostParameter[] params,
			HttpsURLConnection connection, String httpMethod) {
		log("Request: ");
		log(httpMethod + " ", url);

		for (String key : requestHeaders.keySet()) {
			connection.addRequestProperty(key, requestHeaders.get(key));
			log(key + ": " + requestHeaders.get(key));
		}
	}

	public void setRequestHeader(String name, String value) {
		requestHeaders.put(name, value);
	}

	public String getRequestHeader(String name) {
		return requestHeaders.get(name);
	}

	private static class NullHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	@SuppressWarnings("deprecation")
	private HttpsURLConnection getConnection(String url) {
		
		try {
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new SecureRandom());
			HttpsURLConnection con = (HttpsURLConnection) new URL(url)
					.openConnection();
			con.setSSLSocketFactory(context.getSocketFactory());
			con.setHostnameVerifier(new HostnameVerifier() {

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					// TODO Auto-generated method stub
					return true;
				}

			});
			if (connectionTimeout > 0 && !isJDK14orEarlier) {
				con.setConnectTimeout(connectionTimeout);
			}
			if (readTimeout > 0 && !isJDK14orEarlier) {
				con.setReadTimeout(readTimeout);
			}
			return con;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + connectionTimeout;
		result = prime * result + readTimeout;
		result = prime * result
				+ ((requestHeaders == null) ? 0 : requestHeaders.hashCode());
		result = prime * result + retryCount;
		result = prime * result + retryIntervalMillis;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpClient other = (HttpClient) obj;
		if (connectionTimeout != other.connectionTimeout)
			return false;
		if (readTimeout != other.readTimeout)
			return false;
		if (requestHeaders == null) {
			if (other.requestHeaders != null)
				return false;
		} else if (!requestHeaders.equals(other.requestHeaders))
			return false;
		if (retryCount != other.retryCount)
			return false;
		if (retryIntervalMillis != other.retryIntervalMillis)
			return false;
		return true;
	}

	private static void log(String message) {
		if (DEBUG) {
			System.out.println("[" + new java.util.Date() + "]" + message);
		}
	}

	private static void log(String message, String message2) {
		if (DEBUG) {
			log(message + message2);
		}
	}

	private static String getCause(int statusCode) {
		String cause = null;
		switch (statusCode) {
		case NOT_MODIFIED:
			break;
		case BAD_REQUEST:
			cause = "The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.";
			break;
		case NOT_AUTHORIZED:
			cause = "Authentication credentials were missing or incorrect.";
			break;
		case FORBIDDEN:
			cause = "The request is understood, but it has been refused.  An accompanying error message will explain why.";
			break;
		case NOT_FOUND:
			cause = "The URI requested is invalid or the resource requested, such as a user, does not exists.";
			break;
		case NOT_ACCEPTABLE:
			cause = "Returned by the Search API when an invalid format is specified in the request.";
			break;
		case INTERNAL_SERVER_ERROR:
			cause = "Something is broken.  Please post to the group so the Qihoo team can investigate.";
			break;
		case BAD_GATEWAY:
			cause = "Qihoo is down or being upgraded.";
			break;
		case SERVICE_UNAVAILABLE:
			cause = "Service Unavailable: The Qihoo servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.";
			break;
		default:
			cause = "";
		}
		return statusCode + ":" + cause;
	}
}
