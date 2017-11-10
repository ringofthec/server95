// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomSuperior implements DatabaseFieldDataBase<CustomSuperior>,Serializable {
    /** 星数量 */
    public int starCount;
    /** 奖励状态 */
    public int awardType;
    public CustomSuperior(){}
    public CustomSuperior(int starCount,int awardType) {
        this.starCount = starCount;
        this.awardType = awardType;
    }
    @Override
    public void set(CustomSuperior other){
        this.starCount = other.starCount;
        this.awardType = other.awardType;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomSuperior other = (CustomSuperior) obj;
        if (this.starCount != other.starCount) return false;
        if (this.awardType != other.awardType) return false;
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