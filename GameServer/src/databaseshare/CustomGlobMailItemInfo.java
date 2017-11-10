// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomGlobMailItemInfo implements DatabaseFieldDataBase<CustomGlobMailItemInfo>,Serializable {
    /** 物品id */
    public int item_id;
    public int getItem_id() { return item_id; }
    
    /** 数量 */
    public int count;
    public int getCount() { return count; }
    
    public CustomGlobMailItemInfo(){}
    public CustomGlobMailItemInfo(int item_id,int count) {
        this.item_id = item_id;
        this.count = count;
    }
    @Override
    public void set(CustomGlobMailItemInfo other){
        this.item_id = other.item_id;
        this.count = other.count;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomGlobMailItemInfo other = (CustomGlobMailItemInfo) obj;
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