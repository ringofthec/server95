package gameserver.event.listener;

import gameserver.network.message.game.ClientMessageChat;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

public class FlushRankListListener implements EventListener {
	
	private final static FlushRankListListener instance = new FlushRankListListener();
	public static FlushRankListListener getInstance() {
		return instance;
	}
	
	@Override
	public void handleEvent(Event e) throws Exception {
		flush();
	}
	
	public void flush() throws Exception{
		//请求排行榜
		ClientMessageChat.getInstance().UpdateRank();
	}
}