package gameserver.http.handler;

import gameserver.http.HttpProcessManager;
import gameserver.utils.ShareException;

import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.http.MyHttpHandler;
import com.commons.util.HttpUtil;
import com.commons.util.HttpUtil.HTTP_METHOD;
import com.commons.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import commonality.ErrorCode;
import commonality.ErrorCodeMessage;
import commonality.MessageConvey;
import commonality.ProcessMessageResult;

public class ProcessMessageHttpHandler extends MyHttpHandler {
	private final static Logger logger = LoggerFactory.getLogger(ProcessMessageHttpHandler.class);
	@Override
	public void handle(HttpExchange httpExchange)  {
		ProcessMessageResult result = new ProcessMessageResult();
		try
		{
			String method = httpExchange.getRequestMethod();
			if (!method.toUpperCase().contains(HTTP_METHOD.POST.getName()))
				throw new ShareException(ErrorCode.CENTER_NOT_POST.ordinal(),"数据不是POST");
			String uri = httpExchange.getRequestURI().toString();
			InputStream stream = httpExchange.getRequestBody();
			byte[] postData = IOUtils.toByteArray(stream);
			Map<String, String> args = HttpUtil.getUrlArgs(uri);
			MessageConvey.ConveyMessageType type = MessageConvey.ConveyMessageType.NONE;
			logger.info("ProcessMessageHttpHandler uri : " + uri);
			if (args.size() > 0) 
				type = MessageConvey.ConveyMessageType.valueOf(Integer.parseInt(args.get(MessageConvey.type)));
			String messageResult = HttpProcessManager.getInstance().Handle(type,args.get(MessageConvey.id),Integer.parseInt(args.get(MessageConvey.condition)),args.get(MessageConvey.conditionargs),postData);
			ErrorCode errorCode = ErrorCode.SUCCEED;
			result.code = errorCode.ordinal();
			result.message = ErrorCodeMessage.getMessage(errorCode);
			result.messageResult = messageResult;
		}
		catch (ShareException e)
		{
			ErrorCode errorCode = ErrorCode.values()[e.getId()];
			String errorMessage = ErrorCodeMessage.getMessage(errorCode,e.getArgs());
			logger.error(errorMessage,e);
			result.code = errorCode.ordinal();
			result.message = errorMessage;
		}
		catch (Exception e)
		{
			ErrorCode errorCode = ErrorCode.CENTER_FAIL;
			String errorMessage = ErrorCodeMessage.getMessage(errorCode,"","",e);
			logger.error("ProcessMessageHttpHandler", e);
			result.code = errorCode.ordinal();
			result.message = errorMessage;
		}
		String response = HttpUtil.urldecode(JsonUtil.ObjectToJson(result));
		logger.info("ProcessMessageHttpHandler handle response : {}",response);
		HttpUtil.sendResponse(httpExchange, response);
	}

	@Override
	public String getPattern() {
		return "/processMessage";
	}

}
