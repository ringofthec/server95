package gameserver.share;

import gameserver.http.HttpProcessManager;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.HttpProcessResult;
import com.commons.util.HttpUtil;
import com.commons.util.JsonUtil;
import com.commons.util.NetUtil;
import com.commons.util.string;
import com.google.protobuf.GeneratedMessage;
import commonality.Common;
import commonality.ErrorCode;
import commonality.GameServerRequestShare;
import commonality.GameServerRequestShareResult;
import commonality.GameToShareHead;
import commonality.PromptType;
import commonality.SHARE_SERVER_TYPE;
import commonality.ShareToGameHead;

public class ShareServer
{
	private static final Logger logger = LoggerFactory.getLogger(ShareServer.class);
	private boolean m_Initialize = false;
	private String m_IP;
	private int m_Port;
	private String m_Session;
	
	private SHARE_SERVER_TYPE m_Type;
	private String m_ID;
	public ShareServer(SHARE_SERVER_TYPE type,String id) { 
		this.m_Type = type;
		this.m_ID = id;
	}
	private boolean RequestAddress() throws Exception
	{
		if (m_Initialize == true) return true;
		GameServerRequestShare request = new GameServerRequestShare(m_Type.Number(), m_ID, "");
		HttpProcessResult result = HttpProcessManager.getInstance().httpPostRequestShare(JsonUtil.ObjectToJson(request));
		String resultString = HttpUtil.toString(result);
		GameServerRequestShareResult shareResult = JsonUtil.JsonToObject(resultString, GameServerRequestShareResult.class);
		if (shareResult.code == ErrorCode.SUCCEED.ordinal())
		{
			m_Initialize = true;
			this.m_IP = shareResult.ip;
			this.m_Port = shareResult.port;
			this.m_Session = shareResult.session;
			return true;
		}
		m_Initialize = false;
		if (shareResult.code == ErrorCode.REQUEST_NOT_EXIST_SERVER.ordinal())
			throw new Exception("不存在有效的分流服务器");
		return false;
	}
	private byte[] GetMessage(GeneratedMessage message)
	{
		GameToShareHead head = new GameToShareHead(m_Type.Number(), m_ID, m_Session);
		ByteBuffer buffer = ByteBuffer.allocate(NetUtil.MAX_WRITE_LENGTH).order(ByteOrder.LITTLE_ENDIAN);
		Util.WriteString(buffer, JsonUtil.ObjectToJson(head));
		buffer.put(NetUtil.GetByeBufferByMessage(Common.NON_BREAKING_MESSAGE_ID, message));
		buffer.flip();
		buffer.rewind();
		byte[] bytes = new byte[buffer.limit()];
		buffer.get(bytes);
		return bytes;
	}
	
	public List<GeneratedMessage> SendMessage(Connection connection,GeneratedMessage message) throws Exception
	{
		return SendMessage_impl(connection, message, 0);
	}
	private List<GeneratedMessage> SendMessage_impl(Connection connection,GeneratedMessage message,int number) throws Exception
	{
		//5次连续发包失败 就直接返回
		if (number > 5) return null;
		if (!RequestAddress()) return null;
		++number;
		HttpProcessResult result = HttpUtil.httpPost(string.Format("http://{}:{}/processMessage", m_IP, m_Port), GetMessage(message));
		//分流服务器已关闭等情况
		if (result.succeed == false)
		{
			m_Initialize = false;
			return SendMessage_impl(connection,message,number);
		}
		ByteBuffer byteBuffer = ByteBuffer.wrap(HttpUtil.toByteArray(result)).order(ByteOrder.LITTLE_ENDIAN);
		String tempstrString = Util.ReadString(byteBuffer);
		
		ShareToGameHead head = JsonUtil.JsonToObject(tempstrString, ShareToGameHead.class);
		//分流服务器已重启等情况
		if (head.code.equals(ErrorCode.SHARE_SESSION_FAIL.ordinal()))
		{
			m_Initialize = false;
			return SendMessage_impl(connection,message,number);
		}
		else if (head.code.equals(ErrorCode.SHARE_SUCCEED.ordinal()))
		{
			return ShareServerManager.getInstance().Handle(connection, m_Type, m_ID, byteBuffer);
		}
		else if (head.code.equals(ErrorCode.SHARE_SEND_PROMPT.ordinal()))
		{
			int id_idx = head.codeMessage.indexOf(",");
			Integer error_code = Integer.parseInt(head.codeMessage.substring(0, id_idx));
			PromptType tp = PromptType.valueOf(error_code);
			throw new GameException(tp, head.codeMessage.substring(id_idx+1));
		}
		else
		{
			logger.error("协议出错 code:{}  message:{}",head.code,head.codeMessage);
			throw new Exception(head.codeMessage);
		}
	}
}
