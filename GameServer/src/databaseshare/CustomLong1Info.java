// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomLong1Info implements DatabaseFieldDataBase<CustomLong1Info>,Serializable {
    /**  */
    public long val1;
    public long getVal1() { return val1; }
    
    public CustomLong1Info(){}
    public CustomLong1Info(long val1) {
        this.val1 = val1;
    }
    @Override
    public void set(CustomLong1Info other){
        this.val1 = other.val1;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomLong1Info other = (CustomLong1Info) obj;
        if (this.val1 != other.val1) return false;
        return true;
    }
    @Override
    public int hashCode() { 
        return 0; 
    }
    @Override
    public boolean isValid() {
        return true;
    }
}