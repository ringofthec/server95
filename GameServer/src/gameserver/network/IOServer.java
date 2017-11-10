package gameserver.network;
import gameserver.config.ServerConfig;
import gameserver.messageprocess.ServerMessageProcessRegister;
import gameserver.messageprocess.base.IMessageProcessRegister;
import gameserver.messageprocess.base.MessageProcess;
import gameserver.network.server.connection.Connection;
import gameserver.thread.ThreadPoolManager;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.Dispatcher;
import com.commons.network.NioServer;
import com.commons.network.ServerCfg;
import com.google.protobuf.GeneratedMessage;

public class IOServer {
	private static final Logger logger = LoggerFactory.getLogger(IOServer.class);
	private final static IOServer instance = new IOServer();
    public final static IOServer getInstance() { return instance; }
    private NioServer nioServer;
    private IMessageProcessRegister msgProcessRegister = new ServerMessageProcessRegister();
    public void start(int port) {
		try {
	        ServerCfg serverCfg = new ServerCfg(ServerConfig.hostname, port, ServerConfig.name, ServerConfig.connectionFactory.newInstance());
	        nioServer = new NioServer(0, ThreadPoolManager.getInstance(), serverCfg);
	        nioServer.startListen();
		} catch (Exception e) {
			logger.error("IOServer is error : ",e);
		}
    }
    public void start(String ip, int port) {
		try {
	        ServerCfg serverCfg = new ServerCfg(ip, port, ServerConfig.name, ServerConfig.connectionFactory.newInstance());
	        nioServer = new NioServer(0, ThreadPoolManager.getInstance(), serverCfg);
	        nioServer.startListen();
		} catch (Exception e) {
			logger.error("IOServer is error : ",e);
		}
    }
    public Dispatcher getDis() {
    	return nioServer.getAcceptDispatcher();
    }
    public int getActiveConnections() {
    	if (nioServer == null)
    		return 0;
    	return nioServer.getActiveConnections();
    }
    public void regMsgProcess(MessageProcess<?> process) {
    	regMsgProcess(process,true);
    }
    public void regMsgProcess(MessageProcess<?> process,boolean checkSession) {
    	msgProcessRegister.regMsgProcess(process,checkSession);
    }
    public void regMsgProcess(Class<? extends GeneratedMessage> messageType,Object processObject,String processMethod) {
    	regMsgProcess(messageType,processObject,processMethod,false);
    }
    public void regMsgProcess(Class<? extends GeneratedMessage> messageType,Object processObject,String processMethod,boolean checkSession) {
    	msgProcessRegister.regMsgProcess(messageType, processObject, processMethod,checkSession);
    }
    
    public void regMsgProcess(Class<? extends GeneratedMessage> messageType,Object processObject,String processMethod,boolean checkSession, int msgId) {
    	msgProcessRegister.regMsgProcess(messageType, processObject, processMethod,checkSession, msgId);
    }
    public void Handle(Connection client, short length, ByteBuffer buffer) {
    	try {
        	((ServerMessageProcessRegister)msgProcessRegister).invokeMessage(client, length, buffer);
    	} catch (Exception e) {
    		logger.error("Handle is error : ",e);
    	}
    }
}
