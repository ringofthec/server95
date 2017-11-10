
import com.commons.management.*;
public class ManagementTest implements ManagementObject {
	
	@Override
	public String getClassName()
	{
		return getClass().getName();
	}
	public boolean Test(String aaa)
	{
		System.out.println("================== " + aaa);
		return true;
		//return new ManagementArg("1616165", "zxcvzxcvzxcv");
	}
	public String Test2(String a,String b)
	{
		return a+b;
	}
}
