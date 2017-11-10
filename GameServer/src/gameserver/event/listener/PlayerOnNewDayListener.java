package gameserver.event.listener;

import gameserver.config.ServerConfig;
import gameserver.jsonprotocol.GDL_G2C_Fallback;
import gameserver.network.message.game.ClientMessageBuild;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.stat.StatManger;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

import com.commons.network.websock.PlayerConManager;
import com.commons.util.TimeUtil;

public class PlayerOnNewDayListener implements EventListener {
	@Override
	public void handleEvent(Event e) throws Exception {
		
		GDL_G2C_Fallback re = new GDL_G2C_Fallback();
		re.setFuncId(2);
		re.setData(" ");
		PlayerConManager.getInstance().broadcastMsg(re);
		
		long nowTime = TimeUtil.GetDateTime();
		for (Connection con : ConnectionManager.GetInstance().getConnections().values()) {
			synchronized (con) {
				if (ServerConfig.country.equals("US")) {
					if (TimeUtil.canFlush(con.getPlayer().getLastFlushTime(), nowTime)) 
					{
						flushPlayer(con);
					}
				}
				else {
					if (!TimeUtil.IsSameDay(con.getPlayer().getLastFlushTime(), nowTime)) 
					{
						flushPlayer(con);
					}
				}
			}
		}
	}

	private void flushPlayer(Connection con) {
		con.getPlayer().onNewDay(TimeUtil.GetDateTime(), false);
		StatManger.getInstance().onNewDay(con);
		ClientMessageBuild.getInstance().sendLoginRewardInfo(con);
	}
}
