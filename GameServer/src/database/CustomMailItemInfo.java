// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomMailItemInfo implements DatabaseFieldDataBase<CustomMailItemInfo>,Serializable {
    /** 物品id */
    public int item_id;
    /** 数量 */
    public int count;
    public CustomMailItemInfo(){}
    public CustomMailItemInfo(int item_id,int count) {
        this.item_id = item_id;
        this.count = count;
    }
    @Override
    public void set(CustomMailItemInfo other){
        this.item_id = other.item_id;
        this.count = other.count;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomMailItemInfo other = (CustomMailItemInfo) obj;
        if (this.item_id != other.item_id) return false;
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