// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomBuyCommodity implements DatabaseFieldDataBase<CustomBuyCommodity>,Serializable {
    /** 物品ID */
    public int commodity_id;
    /** 购买时间 */
    public long buy_time;
    /** 数量 */
    public int number;
    public CustomBuyCommodity(){}
    public CustomBuyCommodity(int commodity_id,long buy_time,int number) {
        this.commodity_id = commodity_id;
        this.buy_time = buy_time;
        this.number = number;
    }
    @Override
    public void set(CustomBuyCommodity other){
        this.commodity_id = other.commodity_id;
        this.buy_time = other.buy_time;
        this.number = other.number;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomBuyCommodity other = (CustomBuyCommodity) obj;
        if (this.commodity_id != other.commodity_id) return false;
        if (this.buy_time != other.buy_time) return false;
        if (this.number != other.number) return false;
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