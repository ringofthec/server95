package gameserver.event.listener;

import gameserver.network.message.game.ClientMessageLegion;
import gameserver.network.protos.common.ProLegion.Msg_C2G_Legion_List;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;
import java.util.Calendar;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;
import com.commons.util.TimeUtil;

public class FushLegionListListener implements EventListener {
	@Override
	public void handleEvent(Event e) throws Exception {
		//0,8,18,24点刷新
		int hour = TimeUtil.GetCalendar().get(Calendar.HOUR_OF_DAY); // 1- 24
		if (hour % 8 != 0)
			return ;
		
		for (Connection connection : ConnectionManager.GetInstance().getConnections().values()) {
			synchronized (connection) {
				Msg_C2G_Legion_List.Builder msg = Msg_C2G_Legion_List.newBuilder();
				msg.setIsHandRefresh(0);
				ClientMessageLegion.getInstance().OnLegionStoreList(connection, msg.build());
			}
		}
	}
}
