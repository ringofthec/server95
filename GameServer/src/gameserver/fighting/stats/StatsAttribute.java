package gameserver.fighting.stats;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StatsAttribute {
	public boolean Valid() default false;
	public int MinValue() default 0;
	public STATS_ENUM MaxValue() default STATS_ENUM.NONE;
}
