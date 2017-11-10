// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomKingWelfareInfo implements DatabaseFieldDataBase<CustomKingWelfareInfo>,Serializable {
    /** 类型 */
    public int type;
    public int getType() { return type; }
    
    /** 领取状态 */
    public boolean isGet;
    public boolean getIsGet() { return isGet; }
    
    /** 最后一次领取时间 */
    public long lastupdatetime;
    public long getLastupdatetime() { return lastupdatetime; }
    
    public CustomKingWelfareInfo(){}
    public CustomKingWelfareInfo(int type,boolean isGet,long lastupdatetime) {
        this.type = type;
        this.isGet = isGet;
        this.lastupdatetime = lastupdatetime;
    }
    @Override
    public void set(CustomKingWelfareInfo other){
        this.type = other.type;
        this.isGet = other.isGet;
        this.lastupdatetime = other.lastupdatetime;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomKingWelfareInfo other = (CustomKingWelfareInfo) obj;
        if (this.type != other.type) return false;
        if (this.isGet != other.isGet) return false;
        if (this.lastupdatetime != other.lastupdatetime) return false;
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