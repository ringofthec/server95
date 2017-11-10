package gameserver.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;

public class HttpUtil {
	private static final int GET_METHOD_INDEX = 0;
	private static final int POST_METHOD_INDEX = 1;

	public static final String GET_METHOD = "get";
	public static final String POST_METHOD = "post";

	public static final int GET_SUCCEED_STATUS = 200; // 请求成功
	public static final int POST_SUCCEED_STATUS = 201; // 上传数据成功
	public static final int PROCESSING_SUCCEED_STATUS = 202; // 请求已经接受但是还没处理

	public static String httpSynSend(String uri, String httpMethod, String body) {
		String aQueryParam = "";

		int method = GET_METHOD_INDEX;
		if (httpMethod.toLowerCase().equals(GET_METHOD)) {
			method = GET_METHOD_INDEX;
		} 
		else if (httpMethod.toLowerCase().equals(POST_METHOD)) {
			method = POST_METHOD_INDEX;
		}

		HttpClient httpClient = new DefaultHttpClient();

		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				10000);

		HttpConnectionParams.setSoTimeout(httpClient.getParams(), 
				10000);

		try {
			HttpResponse response = null;

			switch (method) {
			case GET_METHOD_INDEX : {
				HttpGet httpGet = new HttpGet(uri);
				response = httpClient.execute(httpGet);
				break;
			}
			case POST_METHOD_INDEX : {
				HttpPost httpPost = new HttpPost(uri);
				httpPost.setHeader("Content-Type",
						"application/x-www-form-urlencoded");
				httpPost.setEntity(new StringEntity(body,
						"utf-8"));

				// 关闭Expect:100-Continue握手
				httpPost.getParams().setBooleanParameter(
						CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
				response = httpClient.execute(httpPost);
				break;
			}
			}

			if (response.getStatusLine().getStatusCode() != GET_SUCCEED_STATUS
					&& response.getStatusLine().getStatusCode() != POST_SUCCEED_STATUS) { // 认证出错
				System.err.println("error:" + response.getStatusLine().getStatusCode());
				return null;
			} else {
				return processEntity(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String processEntity(HttpEntity entity)
			throws IllegalStateException, IOException 
			{
		BufferedReader br = new BufferedReader(new InputStreamReader(
				entity.getContent()));
		String line, result = "";
		StringBuilder sBuilder = new StringBuilder(result);
		while ((line = br.readLine()) != null) {
			sBuilder.append(line);
		}
		result = sBuilder.toString();

		return result;
			}
}
