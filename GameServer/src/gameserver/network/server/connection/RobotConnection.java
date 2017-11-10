/**
 * This file is part of Aion Core <aioncore.com>
 *
 *  This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */

package gameserver.network.server.connection;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.NetUtil;
import com.google.protobuf.GeneratedMessage;

public class RobotConnection extends Connection {
	private static final Logger logger = LoggerFactory.getLogger(RobotConnection.class);
	
    public RobotConnection() {
    	super(null);
    }
    
    @Override
    protected boolean writeData(ByteBuffer data) {
    	if (sendMsgQueue.isEmpty()) 
    		return false;
    	
    	Message message = sendMsgQueue.removeFirst();
    	if (message == null) 
    		return false;
    	
    	byte[] bytes = null;
    	int bytelen = 0;
    	if (message.getType() == 0) {
        	String strMessage = "";
        	for (GeneratedMessage msg : message.getMessages())
        		strMessage += (msg.getClass().getSimpleName() + " ");
        	bytes = NetUtil.GetByeBufferByMessageEx(buffer, message.getMsgId(), session, message.getMessages());
        	if (!strMessage.equals("")) 
        		logger.info("SendMessage [0x{}] length [{}] to [{}] messages [{}]",Integer.toHexString(message.msgId),buffer.limit(),getIP(),strMessage);
    	} 
    	else {
    		bytes = NetUtil.GetByeBufferByBytesEx(buffer, message.getMsgId(), "", message.getBytes());
    	}
    	
    	if (bytes == null || bytes.length <= 0) 
    		return false;
    	
    	bytelen = buffer.limit();
    	try {
    		byte[] hehe = bytes;
    		int len = bytelen;
			data.putShort((short)(len + 2));
			data.put(hehe, 0, len);
	    	data.flip();
	    	data.rewind();
	        return true;
		} catch (Exception e) {
			logger.error("writeData error, ", e);
		}
    	return false;
    }
}
