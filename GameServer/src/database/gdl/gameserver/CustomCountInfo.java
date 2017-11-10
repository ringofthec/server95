// 本文件是自动生成，不要手动修改
package database.gdl.gameserver;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomCountInfo implements DatabaseFieldDataBase<CustomCountInfo>,Serializable {
    /** id */
    public int id;
    public int getId() { return id; }
    
    /** 数量 */
    public int count;
    public int getCount() { return count; }
    
    public CustomCountInfo(){}
    public CustomCountInfo(int id,int count) {
        this.id = id;
        this.count = count;
    }
    @Override
    public void set(CustomCountInfo other){
        this.id = other.id;
        this.count = other.count;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomCountInfo other = (CustomCountInfo) obj;
        if (this.id != other.id) return false;
        if (this.count != other.count) return false;
        return true;
    }
    @Override
    public int hashCode() { 
        return 0; 
    }
		@Override
		public boolean isValid() {
			return count > 0;
		}
}