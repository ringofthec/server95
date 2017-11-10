package gameserver.share;

import gameserver.messageprocess.ShareMessageProcessRegister;
import gameserver.messageprocess.base.MessageProcess;
import gameserver.network.server.connection.Connection;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.GeneratedMessage;
import commonality.SHARE_SERVER_TYPE;
public class ShareServerManager {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ShareServerManager.class);
	private final static ShareServerManager instance = new ShareServerManager();
    public static ShareServerManager getInstance() { return instance; }
    
	private Map<SHARE_SERVER_TYPE, Map<String,ShareServer>> m_ServerArray = new HashMap<SHARE_SERVER_TYPE, Map<String,ShareServer>>();
	private ShareMessageProcessRegister messageProcessRegister = new ShareMessageProcessRegister();
	
	
	public List<GeneratedMessage> sendMsgPVP(Connection con, GeneratedMessage message) throws Exception {
		return SendMessage(con, SHARE_SERVER_TYPE.LEGION, 0, message);
	}
	
	public List<GeneratedMessage> sendMsgRank(Connection con, GeneratedMessage message) throws Exception {
		return SendMessage(con, SHARE_SERVER_TYPE.LEGION, 0, message);
	}
	
	public List<GeneratedMessage> sendMsgAccount(Connection con, GeneratedMessage message) throws Exception {
		return SendMessage(con, SHARE_SERVER_TYPE.LEGION, 0, message);
	}
	
	public List<GeneratedMessage> sendMsgShop(Connection con, GeneratedMessage message) throws Exception {
		return SendMessage(con, SHARE_SERVER_TYPE.LEGION, 0, message);
	}
	
	public List<GeneratedMessage> sendMsgCreateLegion(Connection con, GeneratedMessage message) throws Exception {
		return SendMessage(con, SHARE_SERVER_TYPE.LEGION, 0, message);
	}
	
	public List<GeneratedMessage> sendMsgLegion(Connection con, int legionId, GeneratedMessage message) throws Exception {
		return SendMessage(con, SHARE_SERVER_TYPE.LEGION, legionId, message);
	}
	
	private List<GeneratedMessage> SendMessage(Connection connection,SHARE_SERVER_TYPE type,int id,GeneratedMessage message)
		throws Exception
	{
		return SendMessage(connection, type, ((Integer)id).toString(), message);
	}
	private List<GeneratedMessage> SendMessage(Connection connection,SHARE_SERVER_TYPE type,String id,GeneratedMessage message)
		throws Exception
	{
		synchronized (m_ServerArray)
		{
			if (!m_ServerArray.containsKey(type))
				m_ServerArray.put(type, new HashMap<String,ShareServer>());
			if (!m_ServerArray.get(type).containsKey(id))
				m_ServerArray.get(type).put(id, new ShareServer(type,id));
		}
		
		return m_ServerArray.get(type).get(id).SendMessage(connection,message);
	}
	///////////////////////////////消息处理//////////////////////////////////////////////
	public void regMsgProcess(MessageProcess<?> process) {
		regMsgProcess(process, true);
	}

	public void regMsgProcess(MessageProcess<?> process, boolean checkSession) {
		messageProcessRegister.regMsgProcess(process, checkSession);
	}

	public void regMsgProcess(Class<? extends GeneratedMessage> messageType,
			Object processObject, String processMethod) {
		regMsgProcess(messageType, processObject, processMethod, true);
	}

	public void regMsgProcess(Class<? extends GeneratedMessage> messageType,
			Object processObject, String processMethod, boolean checkSession) {
		messageProcessRegister.regMsgProcess(messageType, processObject,
				processMethod, checkSession);
	}

	public void unregMsgProcess(Class<? extends GeneratedMessage> messageType) {
		messageProcessRegister.unregMsgProcess(messageType);
	}

	public void unregAllMsgProcess() {
		messageProcessRegister.unregAllMsgProcess();
	}
	
	public List<GeneratedMessage> Handle(Connection connection,SHARE_SERVER_TYPE type,String id,ByteBuffer buffer) {
		return messageProcessRegister.invokeMessage(connection,type,id,buffer);
	}
}
