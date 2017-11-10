// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomInstanceStar implements DatabaseFieldDataBase<CustomInstanceStar>,Serializable {
    /** ID */
    public int id;
    /** 星数量 */
    public int starCount;
    /** 挑战次数 */
    public int count;
    /** 是否是首杀 */
    public byte type;
    public CustomInstanceStar(){}
    public CustomInstanceStar(int id,int starCount,int count,byte type) {
        this.id = id;
        this.starCount = starCount;
        this.count = count;
        this.type = type;
    }
    @Override
    public void set(CustomInstanceStar other){
        this.id = other.id;
        this.starCount = other.starCount;
        this.count = other.count;
        this.type = other.type;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomInstanceStar other = (CustomInstanceStar) obj;
        if (this.id != other.id) return false;
        if (this.starCount != other.starCount) return false;
        if (this.count != other.count) return false;
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