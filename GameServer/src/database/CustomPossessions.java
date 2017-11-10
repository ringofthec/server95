// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomPossessions implements DatabaseFieldDataBase<CustomPossessions>,Serializable {
    /** tableId */
    public int id;
    /** 数量 */
    public int number;
    /** 等级 */
    public int level;
    public CustomPossessions(){}
    public CustomPossessions(int id,int number,int level) {
        this.id = id;
        this.number = number;
        this.level = level;
    }
    @Override
    public void set(CustomPossessions other){
        this.id = other.id;
        this.number = other.number;
        this.level = other.level;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomPossessions other = (CustomPossessions) obj;
        if (this.id != other.id) return false;
        if (this.number != other.number) return false;
        if (this.level != other.level) return false;
        return true;
    }
    @Override
    public int hashCode() { 
        return 0; 
    }
    @Override
    public boolean isValid() {
        return number > 0;
    }
}