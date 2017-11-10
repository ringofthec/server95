package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Table_GFRandom extends MT_TableBase {
	final String FILE_MD5_CODE = "0dba2bf88b87d4884a81bac2f1069b9c";
    private String fileName = "";
	private Boolean newFlag = true;
    private HashMap<Integer,MT_Data_GFRandom> m_dataArray = new HashMap<Integer,MT_Data_GFRandom>();

    public MT_Table_GFRandom (String fileName) {
        this.fileName = fileName;
    }

    public void Initialize() throws Exception {
        m_dataArray.clear();
        byte[] buffer = TableUtil.GetBuffer(fileName);
        ByteBuffer reader = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
        Integer iRow = reader.getInt();
        Integer iColums = reader.getInt();
        Integer iCodeNum = reader.getInt();

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
            MT_Data_GFRandom pData = MT_Data_GFRandom.ReadMemory(reader, fileName);
            if (Contains(pData.ID()))
                throw new Exception("文件" + fileName + "有重复项 ID : " + pData.ID());
            else
                m_dataArray.put(pData.ID(),pData);
        }
		TableUtil.Debug("Table"+fileName+" Initialize Finish");
    }
            
    @Override
    public Boolean Contains(Integer ID) {
        return m_dataArray.containsKey(ID);
    }

	public MT_Data_GFRandom GetElement(Integer ID) {
		if (Contains(ID))
			return m_dataArray.get(ID);
        TableUtil.Warning("MT_Data_GFRandom key is not exist " + ID);
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

	public final HashMap<Integer,MT_Data_GFRandom> Datas() {
		return m_dataArray;
	}

	public Boolean GetNewFlag() {
		return newFlag;
	}

	public Boolean SetNewFlag(Boolean flag) {
		newFlag = flag;
		return newFlag;
	}

}
