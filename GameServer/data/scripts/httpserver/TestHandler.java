import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.CharUtils;

import com.commons.http.MyHttpHandler;
import com.commons.util.CharsetUtil;
import com.commons.util.GZipUtil;
import com.commons.util.HttpUtil;
import com.sun.net.httpserver.HttpExchange;

public class TestHandler implements MyHttpHandler{

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		//HttpUtil.sendResponse(arg0, GZipUtil.Compress("vjroawejroewajroij福建饿哦阿文ifjxxxxxxxxxxxxxxxxxxxxxxxxccccccccccccccccccccvvvvvvvvvvvvvvvvvvvvvveeeeeeeeeeeeeeeewwwwwwwwwwwwwwwwwo哇阿胶foe我奥法叫我俄爱福建我啊阿胶服务饿啊".getBytes(CharsetUtil.defaultCharset)));
	}

	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return "/test";
	}

}
