package gameserver.http.handler;

import java.io.InputStream;
import java.net.InetSocketAddress;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.http.MyHttpHandler;
import com.commons.util.CharsetUtil;
import com.commons.util.HttpUtil;
import com.commons.util.HttpUtil.HTTP_METHOD;
import com.sun.net.httpserver.HttpExchange;

public class MyInvalidHttpHandler extends MyHttpHandler {
	private final static Logger logger = LoggerFactory.getLogger(MyInvalidHttpHandler.class);
	@Override
	public void handle(HttpExchange httpExchange) {
		try
		{
			// TODO Auto-generated method stub
			String method = httpExchange.getRequestMethod();
			String uri = httpExchange.getRequestURI().toString();
			InetSocketAddress address = httpExchange.getRemoteAddress();
			logger.debug("接收到http请求[{}]  uri : '{}'  method : '{}'",address,uri,method);
			if (method.toUpperCase().contains(HTTP_METHOD.POST.getName()))
			{
				InputStream stream = httpExchange.getRequestBody();
				String postData = IOUtils.toString(stream,CharsetUtil.defaultCharset);
				logger.debug("postdata : '{}'",postData);
			}
		}
		catch (Exception e)
		{
			logger.error("MyInvalidHttpHandler handle is error : ",e);
		}
		String response = "Invalid Address";
		HttpUtil.sendResponse(httpExchange, response);
	}
	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return "/";
	}
	
}
