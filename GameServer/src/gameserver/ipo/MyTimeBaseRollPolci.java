package gameserver.ipo;

import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

import com.commons.util.TimeUtil;

public class MyTimeBaseRollPolci<E> extends TimeBasedRollingPolicy<E>{
	@Override
	public String getActiveFileName() {
		String filename = super.getActiveFileName();
		int firstChar = filename.indexOf("/");
		int secondChar = filename.lastIndexOf("/");
		String file = filename.substring(0, firstChar + 1) + 
				TimeUtil.GetDateString(System.currentTimeMillis()).substring(0, 10) +
				filename.substring(secondChar);
		return file;
	}
}
