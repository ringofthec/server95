// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomFormation implements DatabaseFieldDataBase<CustomFormation>,Serializable {
    /** 坐标X */
    public int x;
    public int getX() { return x; }
    
    /** 坐标Y */
    public int y;
    public int getY() { return y; }
    
    /** 兵种ID */
    public int id;
    public int getId() { return id; }
    
    /** 兵种类型 0普通兵种 1英雄 */
    public int type;
    public int getType() { return type; }
    
    /** 是否有人士兵 0没有 1有 */
    public boolean is_used;
    public boolean getIs_used() { return is_used; }
    
    public CustomFormation(){is_used = true;}
    public CustomFormation(int x,int y,int id,int type,boolean is_used) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.type = type;
        this.is_used = is_used;
    }
    @Override
    public void set(CustomFormation other){
        this.x = other.x;
        this.y = other.y;
        this.id = other.id;
        this.type = other.type;
        this.is_used = other.is_used;
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
        if (this.is_used != other.is_used) return false;
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