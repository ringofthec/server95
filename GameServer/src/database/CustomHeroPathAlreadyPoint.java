// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomHeroPathAlreadyPoint implements DatabaseFieldDataBase<CustomHeroPathAlreadyPoint>,Serializable {
    /** 关卡ID */
    public int id;
    /** 关卡星数 */
    public int starCount;
    public CustomHeroPathAlreadyPoint(){}
    public CustomHeroPathAlreadyPoint(int id,int starCount) {
        this.id = id;
        this.starCount = starCount;
    }
    @Override
    public void set(CustomHeroPathAlreadyPoint other){
        this.id = other.id;
        this.starCount = other.starCount;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomHeroPathAlreadyPoint other = (CustomHeroPathAlreadyPoint) obj;
        if (this.id != other.id) return false;
        if (this.starCount != other.starCount) return false;
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