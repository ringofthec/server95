package gameserver.event.listener;

import gameserver.config.ServerConfig;
import gameserver.event.GameEventDispatcher;
import gameserver.jsonprotocol.FuncSetting;
import gameserver.jsonprotocol.GDL_G2C_FunctionOpen;

import java.util.Calendar;

import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

import com.commons.network.websock.PlayerConManager;
import com.commons.thread.WorldEvents;
import com.commons.util.TimeUtil;

public class NewHourListener implements EventListener {
	@Override
	public void handleEvent(Event e) throws Exception {
		GDL_G2C_FunctionOpen fun_open = new GDL_G2C_FunctionOpen();
		if (!ServerConfig.app_test) {
			Calendar calendar2 = TimeUtil.GetCalendar();
	    	if (calendar2.get(Calendar.HOUR_OF_DAY) >= 0 && calendar2.get(Calendar.HOUR_OF_DAY) <= 7) {
//	    		for (int fi = 1; fi <= 3; ++fi) {
//					FuncSetting alipay = new FuncSetting();
//					alipay.setFunId(fi);
//					alipay.setOpen(0);
//					fun_open.getFuncs().add(alipay);
//				}
//	    		
//	    		PlayerConManager.getInstance().broadcastMsg(fun_open);
	    	}
		}
	}
	
	public static void regi() {
		GameEventDispatcher.getInstance().addListener(WorldEvents.TIME_TICK_HOUR, new NewHourListener());
	}
}
