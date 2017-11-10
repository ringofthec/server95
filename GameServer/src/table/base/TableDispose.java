package table.base;
import table.TableUtil;
import gameserver.config.TableConfig;
import gameserver.utils.Util;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.GZipUtil;
import com.commons.util.string;

public class TableDispose implements TableUtil.ITableUtil {
	
	
	@Override
	public void Debug(String str) {
		// TODO Auto-generated method stub
		
	}
	private final static Logger logger = LoggerFactory.getLogger(TableDispose.class);
	@Override
    public String ReadString(ByteBuffer buffer)  {
    	return Util.ReadString(buffer);
    }
    @Override
    public String ReadFString(ByteBuffer buffer)
    {
        String str = ReadString(buffer);
        Pattern p = Pattern.compile("\\{(\\d+)\\}");
        Matcher m = p.matcher(str);
        while(m.find())
        {
        	str = str.replace(m.group(), "{}");
        }
        return str;
    }
	@Override
	public String ReadLString(ByteBuffer reader, String fileName,String lineName, int id) {
		if (TableManager.GetInstance() == null)
			return "";
		
		return TableManager.GetInstance().GetLanguageText(string.Format("{}_{}_{}", fileName, lineName, id));
	}
	@Override
	public String ReadLString(ByteBuffer reader, String fileName,String lineName, String id) {
		if (TableManager.GetInstance() == null)
			return "";
		
		return TableManager.GetInstance().GetLanguageText(string.Format("{}_{}_{}", fileName, lineName, id));
	}
    public byte[] GetBuffer(String str)
    {
    	try {
    		String fileName = TableConfig.tablePath + File.separatorChar + str + TableConfig.tableSuffix;
			return GZipUtil.Decompress(FileUtils.openInputStream(FileUtils.getFile(fileName)));
		} catch (IOException e) {
			logger.error("GetBuffer is error : ",e);
		}
		return null;
    }
	public void Warning(String str)
	{
		logger.warn(str);
	}
	public void Error(String str,Exception e)
	{
		logger.error(str,e);
	}

}
