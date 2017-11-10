package gameserver.event.listener;

import com.gdl.game.FishGameConfig;

import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;
import table.base.TableManager;

public class CheckTableChangeListener implements EventListener {
	@Override
	public void handleEvent(Event e) throws Exception {
		TableManager.GetInstance().checkTableTime();
		FishGameConfig.getInstance().checkTableTime();
	}
}
