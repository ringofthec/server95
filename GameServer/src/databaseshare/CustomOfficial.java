// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomOfficial implements DatabaseFieldDataBase<CustomOfficial>,Serializable {
    /** 官员ID */
    public long id;
    public long getId() { return id; }
    
    public CustomOfficial(){}
    public CustomOfficial(long id) {
        this.id = id;
    }
    @Override
    public void set(CustomOfficial other){
        this.id = other.id;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomOfficial other = (CustomOfficial) obj;
        if (this.id != other.id) return false;
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