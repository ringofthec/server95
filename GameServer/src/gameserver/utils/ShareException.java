package gameserver.utils;
import com.commons.util.string;
public class ShareException extends Exception {
	private static final long serialVersionUID = 1L;
	private int m_id;			//异常ID
	private Object[] m_args;	//异常参数
	public int getId() { return m_id; }				//返回异常ID
	public Object[] getArgs() { return m_args; }	//返回异常参数
	public ShareException(String format,Object... args)
	{
		this(-1,format,args);
	}
	
	public ShareException(int id)
	{
		this(id,"");
	}
	public ShareException(int id, String format,Object... args)
	{
		super("id:" + id + " error:" + string.Format(format, args));
		this.m_id = id;
		this.m_args = args;
	}
}
