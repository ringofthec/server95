// 本文件是自动生成，不要手动修改
package database;
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
//================勋章表===========================
@SuppressWarnings("unused")
public class DatabaseMedal implements DatabaseTableDataBase<DatabaseMedal>,Serializable {
    public static final String TableName = "Medal";
    /** 勋章表唯一ID */
    @DatabaseFieldAttribute(fieldName = "medal_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer medal_id = null;
    public Integer getMedal_id() {return medal_id;}
    /** 英雄唯一ID */
    @DatabaseFieldAttribute(fieldName = "hero_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer hero_id = null;
    public Integer getHero_id() {return hero_id;}
    /** 勋章属性表table表id */
    @DatabaseFieldAttribute(fieldName = "medal_table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer medal_table_id = null;
    public Integer getMedal_table_id() {return medal_table_id;}
    /** 当前勋章星级 */
    @DatabaseFieldAttribute(fieldName = "cur_star",fieldType = Integer.class,arrayType = Byte.class)
    public Integer cur_star = null;
    public Integer getCur_star() {return cur_star;}
    /** 当前勋章等级 */
    @DatabaseFieldAttribute(fieldName = "cur_level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer cur_level = null;
    public Integer getCur_level() {return cur_level;}
    /** 当前勋章经验 */
    @DatabaseFieldAttribute(fieldName = "cur_exp",fieldType = Integer.class,arrayType = Byte.class)
    public Integer cur_exp = null;
    public Integer getCur_exp() {return cur_exp;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseMedal __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseMedal();
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
    public DatabaseMedal diff()
    {
        DatabaseMedal ret = new DatabaseMedal();
        if (this.medal_id != null && (__self == null || !this.medal_id.equals(__self.medal_id)))
            ret.medal_id = this.medal_id;
        if (this.hero_id != null && (__self == null || !this.hero_id.equals(__self.hero_id)))
            ret.hero_id = this.hero_id;
        if (this.medal_table_id != null && (__self == null || !this.medal_table_id.equals(__self.medal_table_id)))
            ret.medal_table_id = this.medal_table_id;
        if (this.cur_star != null && (__self == null || !this.cur_star.equals(__self.cur_star)))
            ret.cur_star = this.cur_star;
        if (this.cur_level != null && (__self == null || !this.cur_level.equals(__self.cur_level)))
            ret.cur_level = this.cur_level;
        if (this.cur_exp != null && (__self == null || !this.cur_exp.equals(__self.cur_exp)))
            ret.cur_exp = this.cur_exp;
        return ret;
    }
    @Override
    public void set(DatabaseMedal value) {
        this.medal_id = value.medal_id;
        this.hero_id = value.hero_id;
        this.medal_table_id = value.medal_table_id;
        this.cur_star = value.cur_star;
        this.cur_level = value.cur_level;
        this.cur_exp = value.cur_exp;
    }
    @Override
    public boolean isEmpty() {
        if (this.medal_id != null) return false;
        if (this.hero_id != null) return false;
        if (this.medal_table_id != null) return false;
        if (this.cur_star != null) return false;
        if (this.cur_level != null) return false;
        if (this.cur_exp != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.medal_id = null;
        this.hero_id = null;
        this.medal_table_id = null;
        this.cur_star = null;
        this.cur_level = null;
        this.cur_exp = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.medal_id != null)
            ret += ("medal_id = " + this.medal_id.toString() + " ");
        if (this.hero_id != null)
            ret += ("hero_id = " + this.hero_id.toString() + " ");
        if (this.medal_table_id != null)
            ret += ("medal_table_id = " + this.medal_table_id.toString() + " ");
        if (this.cur_star != null)
            ret += ("cur_star = " + this.cur_star.toString() + " ");
        if (this.cur_level != null)
            ret += ("cur_level = " + this.cur_level.toString() + " ");
        if (this.cur_exp != null)
            ret += ("cur_exp = " + this.cur_exp.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "Medal";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "medal_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return medal_id;
    }
}