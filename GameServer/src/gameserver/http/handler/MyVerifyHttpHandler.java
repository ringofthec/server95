package gameserver.http.handler;
import java.io.IOException;
import com.commons.http.MyHttpHandler;
import com.commons.util.HttpUtil;
import com.sun.net.httpserver.HttpExchange;

public class MyVerifyHttpHandler extends MyHttpHandler {
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		HttpUtil.sendResponse(httpExchange, "succeed");
	}
	@Override
	public String getPattern() {
		return "/verify";
	}
}
