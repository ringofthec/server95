// 本文件是自动生成，不要手动修改
package database.gdl.gameserver;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomGiftShow implements DatabaseFieldDataBase<CustomGiftShow>,Serializable {
    /** 物品tableid */
    public int id;
    public int getId() { return id; }
    
    /** 物品数量 */
    public int num;
    public int getNum() { return num; }
    
    /** 到期时间 */
    public String exp_time;
    public String getExp_time() { return exp_time; }
    
    public CustomGiftShow(){}
    public CustomGiftShow(int id,int num,String exp_time) {
        this.id = id;
        this.num = num;
        this.exp_time = exp_time;
    }
    @Override
    public void set(CustomGiftShow other){
        this.id = other.id;
        this.num = other.num;
        this.exp_time = other.exp_time;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomGiftShow other = (CustomGiftShow) obj;
        if (this.id != other.id) return false;
        if (this.num != other.num) return false;
        if (!this.exp_time.equals(other.exp_time)) return false;
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