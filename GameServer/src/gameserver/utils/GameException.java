package gameserver.utils;
import com.commons.util.string;

import commonality.PromptType;
public class GameException extends Exception {
	private static final long serialVersionUID = 1L;
	private PromptType m_id;			//异常ID
	private Object[] m_args;	//异常参数
	public PromptType getId() { return m_id; }				//返回异常ID
	public Object[] getArgs() { return m_args; }	//返回异常参数
	
	public GameException(PromptType type, String format,Object... args)
	{
		super("id:" + type + " error:" + string.Format(format, args));
		this.m_id = type;
		this.m_args = args;
	}
	
	public GameException(PromptType type,Object... args) {
		this.m_id = type;
		this.m_args = args;
	}
	
	public GameException(String format,Object... args)
	{
		super(" error:" + string.Format(format, args));
		this.m_id = PromptType.NONE;
		this.m_args = args;
	}
	
}
