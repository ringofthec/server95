package gameserver.fighting.stats;
import gameserver.fighting.int2;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class StatsBase {
	private static final Logger logger = LoggerFactory.getLogger(StatsBase.class);
	private Map<STATS_ENUM, Value> mStats = new HashMap<STATS_ENUM, Value>();
    class Value
    {
        public Object value;
        public StatsAttribute attribute;
        public Value(Object value, StatsAttribute attribute)
        {
            this.value = value;
            this.attribute = attribute;
        }
    }
    Object Min(Object val1, Object val2)
    {
        if (val1 == null || val2 == null || val1.getClass() != val2.getClass())
            return null;
        Class<?> type = val1.getClass();
        if (type == Byte.class || type == Byte.TYPE)
        {
            return Math.min((Byte)val1, (Byte)val2);
        }
        else if (type == Short.class || type == Short.TYPE)
        {
            return Math.min((Short)val1, (Short)val2);
        }
        else if (type == Integer.class || type == Integer.TYPE)
        {
            return Math.min((Integer)val1, (Integer)val2);
        }
        else if (type == Long.class || type == Long.TYPE)
        {
            return Math.min((Long)val1, (Long)val2);
        }
        return null;
    }
    Object Max(Object val1, Object val2)
    {
    	Object val = Min(val1, val2);
    	if (val == null) 
    		return null;
    	else if (val.equals(val1))
            return val2;
        else if (val.equals(val2))
            return val1;
        return null;
    }
    public Object SetValue(STATS_ENUM type, Object value)
    {
        if (mStats.containsKey(type))
        {
            Value val = mStats.get(type);
            Object minValue = null, maxValue = null;
            if (val.attribute != null)
            {
            	if (val.attribute.Valid())
            	{
            		minValue = val.attribute.MinValue();
            		if (val.attribute.MaxValue() != STATS_ENUM.NONE) 
            			maxValue = GetValue(val.attribute.MaxValue());
            	}
            }
            if (minValue != null)
            {
                value = Max(value, minValue);
            }
            if (maxValue != null)
            {
                value = Min(value, maxValue);
            }
            if (val != null)
            {
                val.value = value;
            }
            return value;
        }
        else
        {
            if (InitializeValue(type))
                return SetValue(type, value);
        }
        return null;
    }
    public Object GetValue(STATS_ENUM type)
    {
        if (mStats.containsKey(type))
            return mStats.get(type).value;
        if (InitializeValue(type))
            return GetValue(type);
        return null;
    }
    boolean InitializeValue(STATS_ENUM type)
    {
    	try {
    		Field field = type.getClass().getField(type.toString());
    		if (field == null)
    			return false;
        	if (field.isAnnotationPresent(StatsAttribute.class))
        	{
        		StatsAttribute statsAttribute = field.getAnnotation(StatsAttribute.class);
        		mStats.put(type, new Value(0, statsAttribute));
        		return true;
        	}
            return false;
		} catch (Exception e) {
			logger.error("InitializeValue is error : ", e);
		}
    	return false;
    }
    public void ClearValue()
    {
        mStats.clear();
    }
    public void Set(StatsBase stats)
    {
        for (Map.Entry<STATS_ENUM, Value> pair : stats.mStats.entrySet())
        {
            SetValue(pair.getKey(), pair.getValue().value);
        }
    }
    public Object UpdateValue(STATS_ENUM type, int2... plus)
    {
        return SetValue(type, GetPlusValue_impl((int)GetValue(type), plus));
    }
    public int GetPlusValue_impl(int value, int2... vals)
    {
        int v = value;
        float f = 0;
        int length = vals.length;
        for (int i = 0; i < length;++i )
        {
            v += vals[i].field1;
            f += vals[i].field2;
        }
        return (int)Math.floor(v * (1 + f / 10000f));
    }
}
