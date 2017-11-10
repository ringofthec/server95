// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomHeroEquip implements DatabaseFieldDataBase<CustomHeroEquip>,Serializable {
    /** 装备部位 */
    public int itemPart;
    /** 装备的Id,数据库item表的主键 */
    public int equipId;
    public CustomHeroEquip(){}
    public CustomHeroEquip(int itemPart,int equipId) {
        this.itemPart = itemPart;
        this.equipId = equipId;
    }
    @Override
    public void set(CustomHeroEquip other){
        this.itemPart = other.itemPart;
        this.equipId = other.equipId;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomHeroEquip other = (CustomHeroEquip) obj;
        if (this.itemPart != other.itemPart) return false;
        if (this.equipId != other.equipId) return false;
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