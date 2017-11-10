// 本文件是自动生成，不要手动修改
package databaseshare;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.sql.Date;
import java.io.Serializable;
import com.commons.util.Utility;
import com.commons.database.DatabaseSimple;
import com.commons.database.DatabaseFieldAttribute;
import com.commons.database.DatabaseTableDataBase;
import com.commons.database.DatabaseFieldDataBase;
//===========================================
@SuppressWarnings("unused")
public class DatabaseRobot_formation implements DatabaseTableDataBase<DatabaseRobot_formation>,Serializable {
    public static final String TableName = "robot_formation";
    /** robot_id */
    @DatabaseFieldAttribute(fieldName = "robot_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer robot_id = null;
    public Integer getRobot_id() {return robot_id;}
    /** 对应阵型表索引 */
    @DatabaseFieldAttribute(fieldName = "robot_index",fieldType = Integer.class,arrayType = Byte.class)
    public Integer robot_index = null;
    public Integer getRobot_index() {return robot_index;}
    /** 阵形 */
    @DatabaseFieldAttribute(fieldName = "fmlist",fieldType = String.class,arrayType = CustomFormation.class)
    public List<CustomFormation> fmlist = null;
    public List<CustomFormation> getFmlist() {return fmlist;}
    /** 人口 */
    @DatabaseFieldAttribute(fieldName = "population",fieldType = Integer.class,arrayType = Byte.class)
    public Integer population = null;
    public Integer getPopulation() {return population;}
    /** 等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseRobot_formation __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseRobot_formation();
        __self.set(this);
    }
    /** DatabaseSimple对象  用于获取获得此对象的 数据库对象 */
    private DatabaseSimple __simple = null;
    @Override
    public void setDatabaseSimple(DatabaseSimple value) {
        __simple = value;
    }
    @Override
    public DatabaseSimple getDatabaseSimple() {
        return __simple;
    }
    @Override
    public void save() {
        if (__simple != null && getPrimaryKeyName() != null && getPrimaryKeyValue() != null)
            __simple.Update(this);
    }
    @Override
    public DatabaseRobot_formation diff()
    {
        DatabaseRobot_formation ret = new DatabaseRobot_formation();
        if (this.robot_id != null && (__self == null || !this.robot_id.equals(__self.robot_id)))
            ret.robot_id = this.robot_id;
        if (this.robot_index != null && (__self == null || !this.robot_index.equals(__self.robot_index)))
            ret.robot_index = this.robot_index;
        if (this.fmlist != null && (__self == null || !this.fmlist.equals(__self.fmlist)))
            ret.fmlist = this.fmlist;
        if (this.population != null && (__self == null || !this.population.equals(__self.population)))
            ret.population = this.population;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        return ret;
    }
    @Override
    public void set(DatabaseRobot_formation value) {
        this.robot_id = value.robot_id;
        this.robot_index = value.robot_index;
        this.fmlist = Utility.cloneList(value.fmlist);
        this.population = value.population;
        this.level = value.level;
    }
    @Override
    public boolean isEmpty() {
        if (this.robot_id != null) return false;
        if (this.robot_index != null) return false;
        if (this.fmlist != null) return false;
        if (this.population != null) return false;
        if (this.level != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.robot_id = null;
        this.robot_index = null;
        this.fmlist = null;
        this.population = null;
        this.level = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.robot_id != null)
            ret += ("robot_id = " + this.robot_id.toString() + " ");
        if (this.robot_index != null)
            ret += ("robot_index = " + this.robot_index.toString() + " ");
        if (this.fmlist != null)
            ret += ("fmlist = " + this.fmlist.toString() + " ");
        if (this.population != null)
            ret += ("population = " + this.population.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "robot_formation";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (fmlist != null && fmlist.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : fmlist) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    fmlist.remove(data);
		    }
        }
    }
    @Override
    public String getPrimaryKeyName() {
        return "robot_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return robot_id;
    }
}