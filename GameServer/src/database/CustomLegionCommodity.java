// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomLegionCommodity implements DatabaseFieldDataBase<CustomLegionCommodity>,Serializable {
    /** 物品TableID */
    public int table_id;
    /** 是否已经被购买，0:没有被购买过；1：已经被购买过 */
    public int is_buy;
    public CustomLegionCommodity(){}
    public CustomLegionCommodity(int table_id,int is_buy) {
        this.table_id = table_id;
        this.is_buy = is_buy;
    }
    @Override
    public void set(CustomLegionCommodity other){
        this.table_id = other.table_id;
        this.is_buy = other.is_buy;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomLegionCommodity other = (CustomLegionCommodity) obj;
        if (this.table_id != other.table_id) return false;
        if (this.is_buy != other.is_buy) return false;
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