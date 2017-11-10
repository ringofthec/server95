
import com.commons.management.*;
public class ManagementTest2 implements ManagementObject{
	
	@Override
	public String getClassName()
	{
		return getClass().getName();
	}
}
