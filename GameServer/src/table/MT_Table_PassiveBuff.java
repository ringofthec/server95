package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Table_PassiveBuff extends MT_TableBase {
	final String FILE_MD5_CODE = "bf9e5bc87b49326a5a0f4640576c70c0";
    private String fileName = "";
    private HashMap<Integer,MT_Data_PassiveBuff> m_dataArray = new HashMap<Integer,MT_Data_PassiveBuff>();

    public MT_Table_PassiveBuff (String fileName) {
        this.fileName = fileName;
    }

    public void Initialize() throws Exception {
        m_dataArray.clear();
        byte[] buffer = TableUtil.GetBuffer(fileName);
        ByteBuffer reader = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
        Integer iRow = reader.getInt();
        Integer iColums = reader.getInt();
        Integer iCodeNum = reader.getInt();

        String strClassMD5CodeInt3 = TableUtil.ReadString(reader);
        if (!strClassMD5CodeInt3.equals(Int3.MD5))
            throw new Exception("文件" + fileName + "的自定义类[Int3]已被修改");

        String strMD5Code = TableUtil.ReadString(reader);
        if (!strMD5Code.equals(FILE_MD5_CODE))
            throw new Exception("文件" + fileName + "版本验证失败");
        for (Integer i = 0; i < iColums; ++i) {
            Integer index = reader.getInt();
            reader.getInt();
            if (index.equals(TableUtil.CLASS_VALUE))
            {
                TableUtil.ReadString(reader);
                Integer nCount = reader.getInt();
                for (Integer k=0; k < nCount; ++k){
                    reader.getInt();
                }
            }
        }
        for (Integer i = 0; i < iRow; ++i) {
            MT_Data_PassiveBuff pData = MT_Data_PassiveBuff.ReadMemory(reader, fileName);
            if (Contains(pData.ID()))
                throw new Exception("文件" + fileName + "有重复项 ID : " + pData.ID());
            else
                m_dataArray.put(pData.ID(),pData);
        }
    }
            
    @Override
    public Boolean Contains(Integer ID) {
        return m_dataArray.containsKey(ID);
    }

	public MT_Data_PassiveBuff GetElement(Integer ID) {
		if (Contains(ID))
			return m_dataArray.get(ID);
        TableUtil.Warning("MT_Data_PassiveBuff key is not exist " + ID);
		return null;
	}

    @Override
	public MT_DataBase GetValue(Integer ID) {
		return GetElement(ID);
	}

    @Override
	public Integer Count() {
		return m_dataArray.size();
	}

	public final HashMap<Integer,MT_Data_PassiveBuff> Datas() {
		return m_dataArray;
	}

}
