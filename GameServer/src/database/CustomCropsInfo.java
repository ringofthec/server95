// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomCropsInfo implements DatabaseFieldDataBase<CustomCropsInfo>,Serializable {
    /** 兵种tableid */
    public int cropTableId;
    /** 兵种等级 */
    public int lv;
    /** 士兵数量 */
    public int num;
    public CustomCropsInfo(){}
    public CustomCropsInfo(int cropTableId,int lv,int num) {
        this.cropTableId = cropTableId;
        this.lv = lv;
        this.num = num;
    }
    @Override
    public void set(CustomCropsInfo other){
        this.cropTableId = other.cropTableId;
        this.lv = other.lv;
        this.num = other.num;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomCropsInfo other = (CustomCropsInfo) obj;
        if (this.cropTableId != other.cropTableId) return false;
        if (this.lv != other.lv) return false;
        if (this.num != other.num) return false;
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