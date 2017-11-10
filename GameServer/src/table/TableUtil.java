package table;
import java.nio.ByteBuffer;
public class TableUtil
{
    public interface ITableUtil
    {
        String ReadString(ByteBuffer reader);
        String ReadFString(ByteBuffer reader);
        String ReadLString(ByteBuffer reader, String fileName, String lineName, int id);
        String ReadLString(ByteBuffer reader, String fileName, String lineName, String id);
        byte[] GetBuffer(String resource);
        void Warning(String str);
		void Debug(String str);
        void Error(String str, Exception e);
    }
    public static final int CLASS_VALUE = 15;
    private static ITableUtil util = null;
    public static void SetUtil(ITableUtil iutil) {
        util = iutil;
    }
    public static String ReadString(ByteBuffer reader)
    {
        return util.ReadString(reader);
    }
    public static String ReadFString(ByteBuffer reader)
    {
        return util.ReadFString(reader);
    }
    public static String ReadLString(ByteBuffer reader, String fileName, String lineName, int id)
    {
        ReadString(reader);
        return util.ReadLString(reader, fileName, lineName, id);
    }
    public static String ReadLString(ByteBuffer reader, String fileName, String lineName, String id)
    {
        ReadString(reader);
        return util.ReadLString(reader, fileName, lineName, id);
    }
    public static byte[] GetBuffer(String resource)
    {
        return util.GetBuffer(resource);
    }
    public static void Warning(String str)
    {
        util.Warning(str);
    }
	public static void Debug(String str)
    {
        util.Debug(str);
    }
    public static void Error(String str, Exception e)
    {
        util.Error(str, e);
    }

    private static final Byte INVALID_BYTE = Byte.MAX_VALUE;
    private static final Short INVALID_INT16 = Short.MAX_VALUE;
    private static final Integer INVALID_INT32 = Integer.MAX_VALUE;
    private static final Long INVALID_INT64 = Long.MAX_VALUE;
    private static final Float INVALID_FLOAT = -1.0f;
    private static final Double INVALID_DOUBLE = -1.0;

    public static boolean IsInvalid(Boolean val)
    {
    	return (val == false);
    }
    public static boolean IsInvalid(Byte val)
    {
        return (val.equals(INVALID_BYTE));
    }
    public static boolean IsInvalid(Short val)
    {
        return (val.equals(INVALID_INT16));
    }
    public static boolean IsInvalid(Integer val)
    {
        return (val.equals(INVALID_INT32));
    }
    public static boolean IsInvalid(Long val)
    {
        return (val.equals(INVALID_INT64));
    }
    public static boolean IsInvalid(Float val)
    {
        return (Math.abs(INVALID_FLOAT - val) < 0.001f);
    }
    public static boolean IsInvalid(Double val)
    {
        return (Math.abs(INVALID_DOUBLE - val) < 0.001f);
    }
    public static boolean IsInvalid(String val)
    {
        return (val == null || val.isEmpty());
    }
    public static <T extends MT_DataBase> boolean IsInvalid(T val)
    {
        if (val == null) return false;
        return val.IsInvalid();
    }
}
