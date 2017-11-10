package gameserver.event.listener;

import gameserver.active.ActiveService;
import gameserver.connection.attribute.ConPlayerAttr;

import java.util.Map;

import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

public class RechargeListener implements EventListener {

	@Override
	public void handleEvent(Event e) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String,Object> args = (Map<String,Object>)e.getContext();
		
		if (!ActiveService.getInstance().isActiveRun(1000))
			return ;
		
		int rechargeCount = (Integer)args.get("count");
		ConPlayerAttr player = (ConPlayerAttr)args.get("player");
		player.getCon().getReward().incActiveParam1(1000, rechargeCount);
	}

}
