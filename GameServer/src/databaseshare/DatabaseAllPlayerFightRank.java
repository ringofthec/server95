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
public class DatabaseAllPlayerFightRank implements DatabaseTableDataBase<DatabaseAllPlayerFightRank>,Serializable {
    public static final String TableName = "allPlayerFightRank";
    /**  */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer id = null;
    public Integer getId() {return id;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家姓名 */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /** 玩家等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 战斗力 */
    @DatabaseFieldAttribute(fieldName = "fightVal",fieldType = Integer.class,arrayType = Byte.class)
    public Integer fightVal = null;
    public Integer getFightVal() {return fightVal;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseAllPlayerFightRank __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseAllPlayerFightRank();
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
    public DatabaseAllPlayerFightRank diff()
    {
        DatabaseAllPlayerFightRank ret = new DatabaseAllPlayerFightRank();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.fightVal != null && (__self == null || !this.fightVal.equals(__self.fightVal)))
            ret.fightVal = this.fightVal;
        return ret;
    }
    @Override
    public void set(DatabaseAllPlayerFightRank value) {
        this.id = value.id;
        this.player_id = value.player_id;
        this.name = value.name;
        this.level = value.level;
        this.fightVal = value.fightVal;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.player_id != null) return false;
        if (this.name != null) return false;
        if (this.level != null) return false;
        if (this.fightVal != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.player_id = null;
        this.name = null;
        this.level = null;
        this.fightVal = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        if (this.fightVal != null)
            ret += ("fightVal = " + this.fightVal.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "allPlayerFightRank";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return id;
    }
}