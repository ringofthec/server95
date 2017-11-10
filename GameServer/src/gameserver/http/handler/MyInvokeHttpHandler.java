package gameserver.http.handler;

import gameserver.management.ManagementManager;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.http.MyHttpHandler;
import com.commons.management.ManagementResult;
import com.commons.util.CharsetUtil;
import com.commons.util.HttpUtil;
import com.commons.util.HttpUtil.HTTP_METHOD;
import com.commons.util.string;
import com.sun.net.httpserver.HttpExchange;
import commonality.ErrorCode;
import commonality.ErrorCodeMessage;

public class MyInvokeHttpHandler extends MyHttpHandler {
	private final static Logger logger = LoggerFactory.getLogger(MyInvokeHttpHandler.class);
	@Override
	public void handle(HttpExchange httpExchange) {
		// TODO Auto-generated method stub
		String method = httpExchange.getRequestMethod();
		String uri = httpExchange.getRequestURI().toString();
		HashMap<Object, Object> ret = new HashMap<>();
		ret.put("code", ErrorCode.FAIL.ordinal());
		ret.put("state", ErrorCode.FAIL.toString());
		try
		{
			if (method.toUpperCase().contains(HTTP_METHOD.POST.getName()))
			{
				InputStream stream = httpExchange.getRequestBody();
				String postData = IOUtils.toString(stream,CharsetUtil.defaultCharset);
				logger.debug("接收到调用函数请求    uri : '{}'  method : '{}'  postdata : '{}'",uri,method,postData);
				ManagementResult result = ManagementManager.getInstance().invoke(postData);
				ErrorCode errorCode = ErrorCode.valueOf(result.errorCode.toString());
				ret.put("code", errorCode.ordinal());
				ret.put("state", errorCode.toString());
				ret.put("message", ErrorCodeMessage.getMessage(errorCode, result.get()));
				ret.put("result", result.resultObject);
				ret.put("args", result.args);
			}
			else
			{
				String errorMessage = string.Format("调用函数请求必须是post数据    uri : '{}'  method : '{}' ",uri,method);
				ret.put("message", errorMessage);
				logger.debug(errorMessage);
			}			
		}
		catch (Exception e)
		{
			String errorMessage = string.Format("MyInvokeHttpHandler.handle is error : ",e);
			ret.put("message", errorMessage);
			logger.error(errorMessage);
		}
		HttpUtil.sendResponse(httpExchange, new JSONObject(ret).toString());
	}
	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return "/invoke";
	}

}
