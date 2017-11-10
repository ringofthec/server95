package gameserver.http.handler;

import java.io.InputStream;
import java.net.InetSocketAddress;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.http.MyHttpHandler;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.handler.LoginHandler;
import com.commons.util.CharsetUtil;
import com.commons.util.HttpUtil;
import com.commons.util.HttpUtil.HTTP_METHOD;
import com.sun.net.httpserver.HttpExchange;

public class ExitHttpHandler extends MyHttpHandler {
	private final static Logger logger = LoggerFactory.getLogger(ExitHttpHandler.class);
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
				logger.debug("will exit1");
				if (postData.equals("dahf34uhfo34yf45g5hg45g45g24gv54rtrn")) {
					logger.debug("will exit2");
					LoginHandler.can_login = false;
					PlayerConManager.getInstance().disAll();
					logger.error("GameServer will shutdown!");
					System.exit(0);
					String response = "Error";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else {
					logger.debug("will exit3");
				}
			}
		}
		catch (Exception e)
		{
			logger.error("ExitHttpHandler handle is error : ",e);
		}
		String response = "Error";
		HttpUtil.sendResponse(httpExchange, response);
	}
	@Override
	public String getPattern() {
		return "/exit";
	}
	
}
