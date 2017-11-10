package gameserver.http;

import gameserver.config.HttpProcessConfig;
import gameserver.config.ServerConfig;
import gameserver.messageprocess.HttpMessageProcessRegister;
import gameserver.messageprocess.base.IMessageProcessRegister;
import gameserver.messageprocess.base.MessageProcess;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.utils.ShareException;
import gameserver.utils.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.HttpProcessListener;
import com.commons.util.HttpProcessResult;
import com.commons.util.HttpUtil;
import com.commons.util.JsonUtil;
import com.commons.util.NetUtil;
import com.commons.util.PathUtil;
import com.commons.util.string;
import com.google.protobuf.GeneratedMessage;
import commonality.Common;
import commonality.ConveyCondition;
import commonality.ErrorCode;
import commonality.GameServerRegister;
import commonality.GameServerRegisterResult;
import commonality.MessageConvey;
import commonality.MessageConvey.ConveyMessageType;
import commonality.REQUEST_TYPE;
import commonality.RegisterField;

public class HttpProcessManager {
	private static Logger logger = LoggerFactory.getLogger(HttpProcessManager.class);
	private final static HttpProcessManager instance = new HttpProcessManager();
    public static HttpProcessManager getInstance() { return instance; }
    private String registerAddress = "";
    private String playerAskLoginAddress = "";
    private String queryDBInfoAddress = "";
    private String processAddress = "";				//中心服务器处理消息地址
    private String conveyAddress = "";				//中心服务器转发消息地址
    
    private String requestShareAddress = "";		//请求分流服务器地址
    private String serverUid = "";					//服务器UID
    private String serverSession = "";				//服务器操作session
    private GameServerRegister m_RegisterData = null;
    private IMessageProcessRegister msgProcessRegister = new HttpMessageProcessRegister();
    public HttpProcessManager()
    {
    	m_RegisterData = new GameServerRegister(0, 
    			PathUtil.GetCurrentDirectory(), 
    			RegisterField.password,
    			NetUtil.GetLocalInetAddress(),
    			string.IsNullOrEmpty(ServerConfig.listenip) ? NetUtil.GetNetworkInetAddress() : ServerConfig.listenip);
    	
    	registerAddress = HttpProcessConfig.address + "register.php";
    	playerAskLoginAddress = HttpProcessConfig.address + "requestPlayerDB.php";
    	queryDBInfoAddress = HttpProcessConfig.address + "requestDBconnectionInfo.php";
    }
    public void setInfo(GameServerRegisterResult result)
    {
    	this.processAddress = result.processAddress;
    	this.conveyAddress = result.conveyAddress;
    	this.requestShareAddress = result.requestShareAddress;
    	this.serverUid = result.uid;
    	this.serverSession = result.session;
    }
    /**获得服务器Uid*/
    public String getUid()
    {
    	return this.serverUid;
    }
    /**获得服务器session*/
    public String getSession()
    {
    	return this.serverSession;
    }
    public HttpProcessResult register() 
    {
    	logger.info("正在请求服务器端口信息...");
    	synchronized (this)
    	{
    		m_RegisterData.type = REQUEST_TYPE.GAMESERVER_REGISTER.Number();
    		return httpPostRegister(m_RegisterData);
    	}
    }
    public HttpProcessResult finish()
    {
    	logger.info("服务器启动完成.");
    	synchronized (this)
    	{
    		m_RegisterData.type = REQUEST_TYPE.GAMESERVER_FINISHED.Number();
    		return httpPostRegister(m_RegisterData);
    	}
    }
    public void shutdown()
    {
    	synchronized (this)
    	{
    		m_RegisterData.type = REQUEST_TYPE.GAMESERVER_QUIT.Number();
    		httpPostRegister(m_RegisterData);
    	}
    }
    ////////////////////////////发送给中心服务器 中心服务器处理逻辑/////////////////////////////////
    /** 发送消息给中心服务器 */
    public HttpProcessResult sendMessage(GeneratedMessage... messages)
    {
    	String strMessage = "";
    	for (GeneratedMessage msg : messages)
    	{
    		if (msg != null) strMessage += (msg.getClass().getSimpleName() + " ");
    	}
    	logger.info("HttpProcessManager.sendMessage 发送消息 : [{}]",strMessage);
    	HttpProcessResult result = httpPostProcess(NetUtil.GetByeBufferByMessage(Common.NON_BREAKING_MESSAGE_ID, this.serverSession, messages)) ;
    	logger.info("HttpProcessManager.sendMessage 返回字符串 : {}",HttpUtil.urldecode(HttpUtil.toString(result)));
    	return result;
    }
    /** 异步发送消息给中心服务器 */    
    public void sendMessageSync(GeneratedMessage... messages)
    {
    	sendMessageSync(null,messages);
    }
    /** 异步发送消息给中心服务器 */
    public void sendMessageSync(final HttpProcessListener listener, GeneratedMessage... messages)
    {
    	String strMessage = "";
    	for (GeneratedMessage msg : messages)
    	{
    		if (msg != null) strMessage += (msg.getClass().getSimpleName() + " ");
    	}
    	logger.info("HttpProcessManager.sendMessageSync 发送消息 : [{}]",strMessage);
    	httpPostProcessSync(NetUtil.GetByeBufferByMessage(Common.NON_BREAKING_MESSAGE_ID, this.serverSession, messages), new HttpProcessListener() {
			@Override
			public void Handle(HttpProcessResult result) {
				logger.info("HttpProcessManager.sendMessageSync 返回字符串 : {}",HttpUtil.urldecode(HttpUtil.toString(result)));
				if (listener != null) listener.Handle(result);
			}
		}) ;
    }
    ////////////////////////////发送给中心服务器 中心服务器处理逻辑/////////////////////////////////
    
    
    //////////////////////////////////中心服务器协议转发（中心服务器不处理直接转发给相应服务器）////////////////////////////////////////////
    
    ////////////
    /** 根据服务器Uid发送消息给其他服务器（也可能是自己） */
    public HttpProcessResult sendMessageToServer(String uid,GeneratedMessage... messages)
    {
    	return sendMessageByUid(false, MessageConvey.ConveyMessageType.TO_SERVER, uid, null, messages);
    }
    /** 异步根据服务器Uid发送消息给其他服务器（也可能是自己） */
    public void sendMessageToServerSync(String uid,GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_SERVER, uid, null, messages);
    }
    /** 异步根据服务器Uid发送消息给其他服务器（也可能是自己） */
    public void sendMessageToServerSync(String uid,HttpProcessListener listener, GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_SERVER, uid, listener, messages);
    }
    ////////////
    /** 根据玩家PlayerId发送消息给其他服务器*/
    public HttpProcessResult sendMessageToPlayer(long playerId,GeneratedMessage... messages)
    {
    	return sendMessageByUid(false, MessageConvey.ConveyMessageType.TO_PLAYER_PLAYERID, ((Long)playerId).toString(), null, messages);
    }
    /** 异步根据玩家PlayerId发送消息给其他服务器*/
    public void sendMessageToPlayerSync(long playerId,GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_PLAYER_PLAYERID, ((Long)playerId).toString(), null, messages);
    }
    /** 异步根据玩家Uid发送消息给其他服务器*/
    public void sendMessageToPlayerSync(long playerId,HttpProcessListener listener,GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_PLAYER_PLAYERID, ((Long)playerId).toString(), listener, messages);
    }
    /** 根据玩家PlayerId发送消息给其他服务器*/
    public HttpProcessResult sendMessageToPlayerClient(long playerId,GeneratedMessage... messages)
    {
    	return sendMessageByUid(false, MessageConvey.ConveyMessageType.TO_PLAYER_PLAYERID_CLIENT, ((Long)playerId).toString(), null, messages);
    }
    /** 异步根据玩家PlayerId发送消息给其他服务器*/
    public void sendMessageToPlayerClientSync(long playerId,GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_PLAYER_PLAYERID_CLIENT, ((Long)playerId).toString(), null, messages);
    }
    /** 异步根据玩家Uid发送消息给其他服务器*/
    public void sendMessageToPlayerClientSync(long playerId,HttpProcessListener listener,GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_PLAYER_PLAYERID_CLIENT, ((Long)playerId).toString(), listener, messages);
    }
    /** 发送消息给所有服务器 */
    public HttpProcessResult sendMessageToAllServer(GeneratedMessage... messages)
    {
    	return sendMessageByUid(false, MessageConvey.ConveyMessageType.TO_ALL_SERVER, serverUid, null, messages);
    }
    /** 异步发送消息给所有服务器 */
    public void sendMessageToAllServerSync(GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_ALL_SERVER, serverUid, null, messages);
    }
    /** 异步发送消息给所有服务器 */
    public void sendMessageToAllServerSync(HttpProcessListener listener, GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_ALL_SERVER, serverUid, listener, messages);
    }
    /** 发送消息给所有玩家（直接发给玩家 服务器只转发不处理）*/
    public HttpProcessResult sendMessageToAllPlayer(GeneratedMessage... messages)
    {
    	return sendMessageByUid(false, MessageConvey.ConveyMessageType.TO_ALL_PLAYER, "", null, messages);
    }
    /** 发送消息给所有玩家（直接发给玩家 服务器只转发不处理）*/
    public HttpProcessResult sendMessageToAllPlayer(ConveyCondition condition,String conditionArgs, GeneratedMessage... messages)
    {
    	return sendMessageByUid(false, MessageConvey.ConveyMessageType.TO_ALL_PLAYER, serverUid,condition, conditionArgs, null, messages);
    }
    /** 异步发送消息给所有玩家（直接发给玩家 服务器只转发不处理）*/
    public void sendMessageToAllPlayerSync(GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_ALL_PLAYER, serverUid, null, messages);
    }
    /** 异步发送消息给所有玩家（直接发给玩家 服务器只转发不处理） */
    public void sendMessageToAllPlayerSync(ConveyCondition condition,String conditionArgs,GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_ALL_PLAYER, serverUid, condition, conditionArgs, null, messages);
    }
    /** 异步发送消息给所有玩家（直接发给玩家 服务器只转发不处理） */
    public void sendMessageToAllPlayerSync(HttpProcessListener listener, GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_ALL_PLAYER, serverUid, listener, messages);
    }
    /** 异步发送消息给所有玩家（直接发给玩家 服务器只转发不处理） */
    public void sendMessageToAllPlayerSync(HttpProcessListener listener,ConveyCondition condition,String conditionArgs,GeneratedMessage... messages)
    {
    	sendMessageByUid(true, MessageConvey.ConveyMessageType.TO_ALL_PLAYER, serverUid, condition, conditionArgs, listener, messages);
    }
    
    private HttpProcessResult sendMessageByUid(boolean sync,MessageConvey.ConveyMessageType type,String id,final HttpProcessListener listener,GeneratedMessage... messages)
    {
    	return sendMessageByUid(sync,type,id,ConveyCondition.NONE,"",listener,messages);
    }
    private HttpProcessResult sendMessageByUid(final boolean sync,final MessageConvey.ConveyMessageType type,final String id,final ConveyCondition condition,final String conditionArgs, final HttpProcessListener listener,GeneratedMessage... messages)
    {
    	final byte[] bytes = NetUtil.GetByeBufferByMessage(Common.NON_BREAKING_MESSAGE_ID, this.serverSession, messages);
    	boolean sendMe = false;
    	boolean finshed = false;
    	if (type == ConveyMessageType.TO_ALL_PLAYER ||
    		type == ConveyMessageType.TO_ALL_SERVER)
    	{
    		sendMe = true;
    		finshed = false;
    	}
    	else if (type == ConveyMessageType.TO_PLAYER_PLAYERID || 
    			 type == ConveyMessageType.TO_PLAYER_PLAYERID_CLIENT)
    	{
    		if (ConnectionManager.GetInstance().GetConnection(Long.parseLong(id)) != null)
    		{
    			sendMe = true;
    			finshed = true;
    		}
    	}
    	else if (type == ConveyMessageType.TO_SERVER)
    	{
    		if (serverUid.equals(id))
    		{
    			sendMe = true;
    			finshed = false;
    		}
    	}
    	
    	if (sendMe)
    	{
    		if (sync == true)
    		{
    			new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Handle(type, id, condition.Number(), conditionArgs, bytes);
						} catch (Exception e) {
							logger.error("Handle is error : ",e);
						}
					}
				}).start();
    		}
    		else
    		{
    			try {
					Handle(type, id, condition.Number(), conditionArgs, bytes);
				} catch (Exception e) {
					logger.error("Handle is error : ",e);
				}
    		}
    	}
    	if (finshed) return null;
    	Map<String,Object> urlArgs = new HashMap<String, Object>();
    	urlArgs.put(MessageConvey.type, type.Number());
    	urlArgs.put(MessageConvey.id, id);
    	urlArgs.put(MessageConvey.condition, condition.Number());
    	urlArgs.put(MessageConvey.conditionargs, conditionArgs);
    	urlArgs.put(MessageConvey.servertype, MessageConvey.GameServerType);
    	if (sync == true)
    	{
    		HttpUtil.httpPostSync(conveyAddress + "?" + HttpUtil.getUrlArgs(urlArgs),bytes,new HttpProcessListener() {
				@Override
				public void Handle(HttpProcessResult result) {
					logger.info("HttpProcessManager.sendMessageByUidSync 返回字符串 : {}",HttpUtil.urldecode(HttpUtil.toString(result)));
					if (listener != null) listener.Handle(result);
				}
			});
    		return null;
    	}
    	else
    	{
    		HttpProcessResult result = HttpUtil.httpPost(conveyAddress + "?" + HttpUtil.getUrlArgs(urlArgs), bytes);
    		logger.info("HttpProcessManager.sendMessageByUid 返回字符串 : {}",HttpUtil.urldecode(HttpUtil.toString(result)));
    		return result;
    	}
    }
    //////////////////////////////////协议转发////////////////////////////////////////////
    private HttpProcessResult httpPostRegister(Object body)
    {
    	return HttpUtil.httpPost(registerAddress, JsonUtil.ObjectToJson(body));
    }
    private HttpProcessResult httpPostProcess(byte[] body)
    {
    	return HttpUtil.httpPost(processAddress, body);
    }
    public HttpProcessResult httpPostAskLogin(Object body)
    {
    	return HttpUtil.httpPost(playerAskLoginAddress, JsonUtil.ObjectToJson(body));
    }
    public HttpProcessResult httpPostQueryDbInfo(Object body)
    {
    	return HttpUtil.httpPost(queryDBInfoAddress, JsonUtil.ObjectToJson(body));
    }
    private void httpPostProcessSync(byte[] body,HttpProcessListener listener)
    {
    	HttpUtil.httpPostSync(processAddress, body, listener);
    }
    public HttpProcessResult httpPostRequestShare(String body)
    {
    	return HttpUtil.httpPost(requestShareAddress, body);
    }
    
    ///////////////////////////////消息处理//////////////////////////////////////////////
    public void regMsgProcess(MessageProcess<?> process)
    {
    	regMsgProcess(process,true);
    }
    public void regMsgProcess(MessageProcess<?> process,boolean checkSession)
    {
    	msgProcessRegister.regMsgProcess(process,checkSession);
    }
    public void regMsgProcess(Class<? extends GeneratedMessage> messageType,Object processObject,String processMethod)
    {
    	regMsgProcess(messageType,processObject,processMethod,true);
    }
    public void regMsgProcess(Class<? extends GeneratedMessage> messageType,Object processObject,String processMethod,boolean checkSession)
    {
    	msgProcessRegister.regMsgProcess(messageType, processObject, processMethod,checkSession);
    }
	public void unregMsgProcess(Class<? extends GeneratedMessage> messageType)
	{
		msgProcessRegister.unregMsgProcess(messageType);
	}
	public void unregAllMsgProcess()
	{
		msgProcessRegister.unregAllMsgProcess();
	}
	public String Handle(MessageConvey.ConveyMessageType type,String id,int condition,String conditionArgs,byte[] postData) throws ShareException
    {
		String messageResult = "";
		ByteBuffer buffer = ByteBuffer.wrap(postData).order(ByteOrder.LITTLE_ENDIAN);
		Short msgId = buffer.getShort();								//取出数据msgID
		if (msgId != Common.NON_BREAKING_MESSAGE_ID)
			throw new ShareException(ErrorCode.CENTER_FORMAT_ERROR.ordinal(),"数据格式错误");
		//TODO
		String session = Util.ReadString(buffer);						//取出session
		if (!getSession().equals(session))
			throw new ShareException(ErrorCode.CENTER_SESSION_FAIL.ordinal(),"服务器session验证失败");
		if (type == MessageConvey.ConveyMessageType.TO_PLAYER_PLAYERID_CLIENT || 
			type == MessageConvey.ConveyMessageType.TO_ALL_PLAYER)
		{
			byte[] bytes = new byte[postData.length - buffer.position()];
			buffer.get(bytes);
			if (type == MessageConvey.ConveyMessageType.TO_ALL_PLAYER)
			{
				ConnectionManager.GetInstance().broadcastMessage(condition,conditionArgs,bytes);
			}
			else if (type == MessageConvey.ConveyMessageType.TO_PLAYER_PLAYERID_CLIENT)
			{
				ConnectionManager.GetInstance().sendMessage(Long.parseLong(id), bytes);
			}
		}
		else
		{
			Short msgNum = buffer.getShort();	//取出包数量
			for (Short i = 0;i < msgNum; ++i)
			{
				String retMessage = ((HttpMessageProcessRegister)msgProcessRegister).invokeMessage(buffer);
				messageResult += retMessage;
			}
		}
		return messageResult;
    }
}
