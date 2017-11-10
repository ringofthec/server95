package gameserver.http;

import gameserver.config.HttpServerConfig;

import com.commons.http.HttpServer;

public class HttpServerManager extends HttpServer {
	private final static HttpServerManager instance = new HttpServerManager();
    public static HttpServerManager getInstance() {
        return instance;
    }
    public void initialize(int port)
    {
    	super.initialize(port,HttpServerConfig.scriptDescriptor);
    }
}
