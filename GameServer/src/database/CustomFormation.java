// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomFormation implements DatabaseFieldDataBase<CustomFormation>,Serializable {
    /** 坐标X */
    public int x;
    /** 坐标Y */
    public int y;
    /** 兵种ID */
    public int id;
    /** 兵种类型 0普通兵种 1英雄 */
    public int type;
    public CustomFormation(){}
    public CustomFormation(int x,int y,int id,int type) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.type = type;
    }
    @Override
    public void set(CustomFormation other){
        this.x = other.x;
        this.y = other.y;
        this.id = other.id;
        this.type = other.type;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomFormation other = (CustomFormation) obj;
        if (this.x != other.x) return false;
        if (this.y != other.y) return false;
        if (this.id != other.id) return false;
        if (this.type != other.type) return false;
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