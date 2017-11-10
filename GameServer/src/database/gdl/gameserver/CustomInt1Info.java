// 本文件是自动生成，不要手动修改
package database.gdl.gameserver;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomInt1Info implements DatabaseFieldDataBase<CustomInt1Info>,Serializable {
    /**  */
    public int val1;
    public int getVal1() { return val1; }
    
    public CustomInt1Info(){}
    public CustomInt1Info(int val1) {
        this.val1 = val1;
    }
    @Override
    public void set(CustomInt1Info other){
        this.val1 = other.val1;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomInt1Info other = (CustomInt1Info) obj;
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