package gameserver.utils;

import gameserver.config.ServerConfig;
import gameserver.connection.attribute.ConBuildAttr;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.protos.game.ProHint;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt.PROMPT_SCENE;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt.PROMPT_TYPE;
import gameserver.network.server.connection.Connection;
import gameserver.thread.ThreadPoolManager;

import java.io.File;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.Int3;
import table.MT_DataBase;
import table.MT_Data_Building;
import table.MT_Data_Item;
import table.MT_Data_Rank;
import table.MT_Data_StringMessage;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.NetUtil;
import com.commons.util.PathUtil;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import commonality.Common;
import commonality.PromptType;

import databaseshare.DatabaseIp_lib;

public class Util {
	private static final Logger logger = LoggerFactory.getLogger(Util.class);
	private static final Random random = new Random();
	
	public static String ReadString(ByteBuffer buffer)  {
		return NetUtil.ReadString(buffer);
    }
    public static void WriteString(ByteBuffer buffer, String text) {
    	NetUtil.WriteString(buffer,text);
    }
    public static String[] GetBuildTableName(MT_Data_Building info) {
    	String spriteName = info.SpriteName();
    	return spriteName.split("_");
    }
    public static boolean isEqiupFragByTableId(int itemTableId) {
    	MT_Data_Item itemdata = TableManager.GetInstance().TableItem().GetElement(itemTableId);
		if (itemdata == null)
			return false;
		return isEqiupFragByType(itemdata.Type());
    }
    public static boolean isEqiupFragByType(int itemType) {
		return itemType == Common.ITEMTYPE.Equip.Number() ||  itemType == Common.ITEMTYPE.Equip_Debris.Number();
    }
    public static int getEqiepOrDesCount(Map<Integer, Integer> items) {
    	int count = 0;
    	for (Entry<Integer, Integer> pair : items.entrySet()) {
    		if (isEqiupFragByTableId(pair.getKey()))
    			count += 1;
    	}
    	return count;
    }
    public static Map<Integer, Integer> getInt2ListToMap(List<Int2> rewards) {
    	Map<Integer, Integer> items = new TreeMap<Integer, Integer>();
    	for (Int2 temp : rewards) {
    		Integer oldcount = items.get(temp.field1());
			if (oldcount == null) oldcount = 0;
			items.put(temp.field1(), oldcount + temp.field2());
    	}
    	return items;
    }
    public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
    public static Map<Integer, Integer> getUtilItemListToMap(List<UtilItem> rewards) {
    	Map<Integer, Integer> items = new TreeMap<Integer, Integer>();
    	for (UtilItem temp : rewards) {
    		Integer oldcount = items.get(temp.GetItemId());
			if (oldcount == null) oldcount = 0;
			items.put(temp.GetItemId(), oldcount + temp.GetCount());
    	}
    	return items;
    }
	public static MT_DataBase GetDataBaseByData(MT_Data_Building info, int level){
		try{
			String spriteName = info.SpriteName();
	        int index = spriteName.indexOf("_");
	        if (index < 0) return null;
	        String key = spriteName.substring(0, index);
	        String value = spriteName.substring(index + 1);
	        return TableManager.GetInstance().getSpawns(key, value).GetValue(level);
		}catch (Exception e){
			logger.error("GetDataBaseByData is error : ", e);
		}
		return null;
	}
	public static Class<?>[] getClassesByCodePath(String path)
	{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String finalPath = PathUtil.GetCurrentDirectory() + "src" + File.separatorChar + path;
		try
		{
			File filePath = FileUtils.getFile(finalPath);
			if (!filePath.exists() || !filePath.isDirectory())
				return classes.toArray(new Class[0]);
			Collection<File> files = FileUtils.listFiles(FileUtils.getFile(finalPath), new String[] { "java" }, true);
			for (File file : files)
			{
				String str = file.getAbsolutePath();
				str = str.replace(PathUtil.GetCurrentDirectory() + "src" + File.separatorChar,"");
				str = str.replace(File.separator, ".");
				str = str.replace(".java", "");
				try
				{
					classes.add(Class.forName(str));
				}
				catch (Exception e)
				{
					logger.debug("Class.forName is error " + str , e);
				}
			}
			return classes.toArray(new Class[0]);
		}
		catch (Exception e)
		{
			logger.info("getClassesByCodePath is error " + finalPath ,e);
		}
		return classes.toArray(new Class[0]);
	}
	public static int rateInt(List<Integer> rates, int randomMax) {
        int rIndex = 0;
        int r = RandomUtil.RangeRandom(1, randomMax);
        int g = 0;
        for (int ra : rates) {
            g += ra;
            if (r <= g) {
                return rIndex;
            }
            rIndex++;
        }
        return -1;
    }
	public static int rateInt3(List<Int3> rates, int randomMax) {
        int rIndex = 0;
        int r = RandomUtil.RangeRandom(1, randomMax);
        int g = 0;
        for (Int3 ra : rates) {
            g += ra.field3();
            if (r <= g) {
                return rIndex;
            }
            rIndex++;
        }
        return -1;
    }
	public static int getRank(int Feat)
	{
        int rank = 0;
        while (true)
        {
            MT_Data_Rank data = TableManager.GetInstance().TableRank().GetElement(rank);
            if (data != null && !TABLE.IsInvalid(data.Exp()) && data.Exp() <= Feat)
                ++rank;
            else
                break;
        }
        return rank;
	}
	
	/**获取可以获取滴军衔最大值*/
	public static int getMaxFeat()
	{
		int feat=0;
		int rank=0;
		while(true)
		{
			 MT_Data_Rank data = TableManager.GetInstance().TableRank().GetElement(rank);
			 if(data != null && !TABLE.IsInvalid(data.Exp()))
				{
				 ++rank;
				 feat=data.Exp();
				 }
			 else
			     break;
		}
		return feat;
	}
	public static int getIntegerSomeBit(int _Resource, int _Mask)
    {
        return _Resource >> _Mask & 1;
    }
	
	public static long getLeftTime() {
		Calendar cal = TimeUtil.GetCalendar();
    	cal.set(Calendar.HOUR_OF_DAY, 23); 
    	cal.set(Calendar.MINUTE, 59); 
    	cal.set(Calendar.SECOND, 59); 
    	cal.set(Calendar.MILLISECOND, 59);
    	return cal.getTimeInMillis() - System.currentTimeMillis();
	}

    public static int setIntegerSomeBit(int a, int _Mask, boolean flag)
    {
        if (flag)
        {
            a |= (0x1 << _Mask);
        }
        else
        {
            a &= ~(0x1 << _Mask);
        }
        return a;
    }
    public static int GetRandom(int max)
    {
    	return random.nextInt(max);
    }
    public static boolean GetOdds(int odds)
    {
    	return (GetRandom(10000) < odds);
    }
    
    /*
     * 获得当前正在工作的数量
     */
    public static int GetCurWorkNum(Connection connect) throws Exception{
		int UpGreadingCount = 0;
		ConBuildAttr connectionBuildAttribute = connect.getBuild();
		Collection<BuildInfo> BuildInfos =  connectionBuildAttribute.getBuildArray();
		for (BuildInfo buildInfo :BuildInfos) {
			buildInfo.checkBuild();
			if (buildInfo.getState() == Proto_BuildState.UPGRADE || buildInfo.getState() == Proto_BuildState.OPERATE_UPGRADE) {
				UpGreadingCount ++;
			}
		}
    	return UpGreadingCount;
    }
    
    public static Msg_G2C_Prompt buildPromptMsg(PromptType promptId, Object... args){
		return buildPromptMsg(promptId, PROMPT_SCENE.NONE, null, args);
	}
	
	public  static Msg_G2C_Prompt buildPromptMsg(PromptType promptId,
			PROMPT_SCENE promptType, String[] scripts, Object... args) {
		Msg_G2C_Prompt.Builder builder = Msg_G2C_Prompt.newBuilder();
    	builder.setPromptId(promptId.Number());
    	builder.setPromptScene(promptType);
    	if (scripts != null && scripts.length > 0) {
    		int length = scripts.length;
    		for (int i=0;i<length;++i)
    			builder.addCallBack(scripts[i]);
    	}
    	int count = args.length;
    	for (int i = 0;i < count;++i) {
    		Object value = args[i];
    		if (value == null) continue;
    		Class<?> type = value.getClass();
    		if (type == Boolean.class || type == Boolean.TYPE) {
    			builder.addArgsList(PROMPT_TYPE.BOOL);
    			builder.addBoolArgs((Boolean)value);
    		} else if (type == Byte.class || type == Byte.TYPE ||
    				 type == Short.class || type == Short.TYPE ||
    				 type == Integer.class || type == Integer.TYPE) {
    			builder.addArgsList(PROMPT_TYPE.INT32);
    			builder.addIntArgs((Integer)value);
    		} else if (type == Long.class || type == Long.TYPE) {
    			builder.addArgsList(PROMPT_TYPE.LONG);
    			builder.addLongArgs((Long)value);		
    		} else if (type == String.class) {
    			builder.addArgsList(PROMPT_TYPE.STRING);
    			builder.addStringArgs((String)value);		
    		} else if (type == TransFormArgs.class) {
    			builder.addArgsList(PROMPT_TYPE.TRANSFORM);
    			TransFormArgs tfa = (TransFormArgs)value;
    			
    			ProHint.Msg_G2C_Prompt.TransInfo.Builder trans = ProHint.Msg_G2C_Prompt.TransInfo.newBuilder();
    			trans.setTablekey(tfa.tablekey);
    			trans.setTablename(tfa.tablename);
    			trans.setId(tfa.id);
    			trans.setColumnkey(tfa.columnkey);
    			trans.setTransfromType(tfa.type);
    			
    			builder.addTransArgs(trans.build());
    		}
    	}
		return builder.build();
	}
	
	public static void sendMail(final String bug) {
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			
			@Override
			public void run() {
				try {
					Email email = new SimpleEmail();
					email.setHostName("smtp.gmail.com");	
					email.setSmtpPort(465);
					email.setAuthentication("toydogbugwork@gmail.com",
							"toydog999");
					email.setSSLOnConnect(true);
					email.setFrom("toydogbugwork@gmail.com","BugMail");
					email.setSubject("BugMail");
					email.setContent(bug, "text/plain;charset=gb2312");
					email.addTo("ring.of.the.c@gmail.com", "413663149@qq.com");
					email.send();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}, 10);
	}
	
	public static long ipToLong(String ipstr) {
		String[] ips = ipstr.split("\\.");
		return Long.parseLong(ips[0]) * 16777216 + 
				Long.parseLong(ips[1]) * 65536 +
				Long.parseLong(ips[2]) * 256 +
				Long.parseLong(ips[3]);
	}
	
	public static String longIpToAddr(long ip) {
		DatabaseIp_lib ipdb = DbMgr.getInstance().getShareDB().SelectOne(DatabaseIp_lib.class, "? >= beginip and ? <= endip limit 1", ip, ip);
		if (ipdb != null)
			return ipdb.getAddr();
		return "UNKOWN";
	}
	
	public static String ipToCountry(String ipstr) {
		return longIpToAddr( ipToLong(ipstr) );
	}
	
	public static void AddValue(HashMap<Integer, Integer> values, int key)
    {
        if (!values.containsKey(key))
            values.put(key, 0);

        values.put(key, (values.get(key) + 1));
    }
	
	public static String GetStringManager(String Key)
    {
        MT_Data_StringMessage stringData = TableManager.GetInstance().TableStringMessage().GetElement(Key);
        if (stringData == null)
            return "";

        return stringData.Text();
    }
	
	public static boolean iscontinueLogin(long now, long last_flush_time) {
		if (ServerConfig.country.equals("US")) {
			long freshTime = TimeUtil.getFreshPoint(now);
			return freshTime - last_flush_time < Common.DAY_MILLISECOND;
		} else {
			return TimeUtil.getDayDiff(now, last_flush_time) == 1;
		}
	}
}
