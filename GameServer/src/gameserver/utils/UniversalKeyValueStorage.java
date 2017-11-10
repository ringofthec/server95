package gameserver.utils;

import gameserver.time.GameTime;

import java.util.Map;

import javolution.util.FastMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.string;

public class UniversalKeyValueStorage {

    private static final Logger logger = LoggerFactory.getLogger(UniversalKeyValueStorage.class);
    private final static int VERSION = 0;
    private Map<String, Object> storage = new FastMap<String, Object>().shared();
    private Map<String, Integer> valueExpireTime = new FastMap<String, Integer>().shared();
    private final String name;

    public UniversalKeyValueStorage(String name) {
        this.name = name;
    }

    public Integer decr(Integer key, Integer delta) {
        return decr(key.toString(), delta);
    }
    
    public Integer decr(Long key, Integer delta) {
        return decr(key.toString(), delta);
    }

    public Integer decr(String key, Integer delta) {
        return incr(key, delta * -1);
    }

    public Integer incr(Integer key, Integer delta) {
        return incr(key.toString(), delta);
    }
    
    public Integer incr(Long key, Integer delta) {
        return incr(key.toString(), delta);
    }

    public Integer incr(String key, Integer delta) {
        assert key != null;
        assert delta != null;

        Integer value = getInt(key, 0);
        Integer newValue = value + delta;

        put(key, newValue);
        return newValue;
    }

    /**
     *
     * @param key
     * @return 返回前先判断该键值是否已经过期，如果已经过期，则返回null，否则返回对应的值
     */
    public Integer getInt(Integer key) {
        return getInt(key, 0);
    }
    
    public Integer getInt(Long key) {
        return getInt(key, 0);
    }

    public Integer getInt(String key) {
        return getInt(key, 0);
    }

    public Object get(String key) {
        return get(key, null);
    }

    private void checkExpires(String key) {
        if (null != valueExpireTime) {
            Integer expiresAt = valueExpireTime.get(key);
            if (null != expiresAt) {
                Integer now = (int) GameTime.getInstance().currentTimeSecond();
                if (now > expiresAt) {
                    storage.remove(key);
                    valueExpireTime.remove(key);
                }
            }
        }
    }

    public Integer getInt(Integer key, Integer defaultValue) {
        return getInt(key.toString(), defaultValue);
    }
    
    public Integer getInt(Long key, Integer defaultValue) {
        return getInt(key.toString(), defaultValue);
    }

    public Integer getInt(String key, Integer defaultValue) {
        Object value = get(key, defaultValue);
        return null == value ? defaultValue : Integer.valueOf(value.toString());
    }

    public Object get(String key, Object defaultValue) {
        assert key != null;
        checkExpires(key);
        Object value = storage.get(key);
        if (null == value) {
            value = defaultValue;
        }
        return value;
    }

    public Integer putInt(Integer key, Integer value) {
        Object oldValue = put(key.toString(), value);
        return null == oldValue ? 0 : Integer.valueOf(value.toString());
    }
    
    public Integer putInt(Long key, Integer value) {
        Object oldValue = put(key.toString(), value);
        return null == oldValue ? 0 : Integer.valueOf(value.toString());
    }

    public Integer putInt(Integer key, Integer value, Integer expiresAt) {
        Object oldValue = put(key.toString(), value, expiresAt);
        return null == oldValue ? 0 : Integer.valueOf(value.toString());
    }

    public Object put(String key, Object value) {
        assert key != null;
        assert value != null;
        Object oldValue = storage.put(key, value);
        return oldValue;
    }

    /**
     *
     * @param key
     * @param expiresAt 超时时间，这是一个时间戳，超过这个时间后这个键值会被自动删除掉
     */
    public void setExpires(Integer key, Integer expiresAt) {
        setExpires(key.toString(), expiresAt);
    }

    public void setExpires(String key, Integer expiresAt) {
        if (null == valueExpireTime) {
            valueExpireTime = new FastMap<String, Integer>().shared();
        }
        valueExpireTime.put(key, expiresAt);
    }

    public Object put(String key, Object value, Integer expiresAt) {
        assert key != null;
        assert expiresAt != null;
        Object oldValue = storage.put(key, value);
        setExpires(key, expiresAt);
        return oldValue;
    }

    public Integer removeInt(Integer key) {
        Object oldValue = remove(key.toString());
        return oldValue == null ? 0 : Integer.valueOf(oldValue.toString());
    }

    public Object remove(String key) {
        if (null != valueExpireTime) {
            valueExpireTime.remove(key);
        }
        return storage.remove(key);
    }

    public void clear() {
        if (null != valueExpireTime) {
            valueExpireTime.clear();
        }
        storage.clear();
    }

    public boolean contains(String key) {
        checkExpires(key);
        return storage.containsKey(key);
    }

    public Map<String, Object> rawGetStorage() {
        return storage;
    }

    public void loadFromString(String str) {

        if (string.IsNullOrEmpty(str)) {
            return;
        }
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(str);

            JSONObject storage = (JSONObject) json.get("storage");
            if (null != storage) {
                for (Object key : storage.keySet()) {
                    this.storage.put(key.toString(), storage.get(key));
                }
            }
            JSONObject valueExpireTime = (JSONObject) json.get("value_expires_time");
            if (null != valueExpireTime) {
                for (Object key : valueExpireTime.keySet()) {
                    this.valueExpireTime.put(key.toString(), new Integer(valueExpireTime.get(key).toString()));
                }
            }
        } catch (Exception e) {
            logger.error("Failed to load universal storage, storage_name={}, raw_json_string={}", name, str, e);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public String toString() {
        String jsonStr = "";
        try {
            JSONObject json = new JSONObject();
            json.put("version", VERSION);
            json.put("storage", new JSONObject(storage));
            json.put("value_expires_time", new JSONObject(valueExpireTime));
            jsonStr = json.toJSONString();
        } catch (Exception e) {
            logger.error("Failed to save universal storage, storage_name={}, jsonStr={}", name, jsonStr, e);
        }
        return jsonStr;
    }
}