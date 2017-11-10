package gameserver.event;

import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventDispatcher;
import net.schst.EventDispatcher.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameEventDispatcher {
	public static final Logger logger = LoggerFactory.getLogger(GameEventDispatcher.class);
	private final static GameEventDispatcher instance = new GameEventDispatcher();
    public final static GameEventDispatcher getInstance() { return instance; }
    
    protected EventDispatcher dispatcher;

    public void addListener(String eventName, EventListener listener) {
        try {
            dispatcher.addListener(eventName, listener);
        } catch (Exception e) {
            logger.error("Failed on add EventListener, eventName={}, listener={}", eventName, listener, e);
        }
    }
    
    public GameEventDispatcher() {
        this.dispatcher = EventDispatcher.getInstance();
    }

     public Event triggerEvent(Event e) {
        try {
            return this.dispatcher.triggerEvent(e, false);
        } catch(Exception ex) {
            String error = "Failed on trigger event, eventName=" + e.getName() + ", error=" + ex.getMessage();
            logger.error(error, ex);
            return null;
        }
     }

    public Event triggerEvent(String name) {
        return this.triggerEvent(new Event(name));
    }

    public Event triggerEvent(String name, Object context) {
        return this.triggerEvent(new Event(name, context));
    }
    
    public Event triggerEvent(String name, Object context, Object userInfo) {
        return this.triggerEvent(new Event(name, context, userInfo));
    }
}
