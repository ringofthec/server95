// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomMillStone implements DatabaseFieldDataBase<CustomMillStone>,Serializable {
    /** 里程碑 */
    public String info;
    public String getInfo() { return info; }
    
    public CustomMillStone(){}
    public CustomMillStone(String info) {
        this.info = info;
    }
    @Override
    public void set(CustomMillStone other){
        this.info = other.info;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomMillStone other = (CustomMillStone) obj;
        if (!this.info.equals(other.info)) return false;
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