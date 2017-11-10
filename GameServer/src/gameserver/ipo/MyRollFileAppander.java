package gameserver.ipo;

import ch.qos.logback.core.rolling.RollingFileAppender;

import com.commons.util.TimeUtil;

public class MyRollFileAppander<E> extends RollingFileAppender<E> {
	@Override
	public void setFile(String file) {
		file = "ipolog/" + TimeUtil.GetDateString(System.currentTimeMillis()).substring(0, 10) + "/" + file;
		super.setFile(file);
	}
}
