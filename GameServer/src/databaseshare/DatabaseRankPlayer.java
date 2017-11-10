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
//================玩家信息表===========================
@SuppressWarnings("unused")
public class DatabaseRankPlayer implements DatabaseTableDataBase<DatabaseRankPlayer>,Serializable {
    public static final String TableName = "rankPlayer";
    /** 玩家ID */
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
    /** 功勋 */
    @DatabaseFieldAttribute(fieldName = "feat",fieldType = Integer.class,arrayType = Byte.class)
    public Integer feat = null;
    public Integer getFeat() {return feat;}
    /** 战斗胜利总次数 */
    @DatabaseFieldAttribute(fieldName = "victorycount",fieldType = Integer.class,arrayType = Byte.class)
    public Integer victorycount = null;
    public Integer getVictorycount() {return victorycount;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseRankPlayer __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseRankPlayer();
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
    public DatabaseRankPlayer diff()
    {
        DatabaseRankPlayer ret = new DatabaseRankPlayer();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.feat != null && (__self == null || !this.feat.equals(__self.feat)))
            ret.feat = this.feat;
        if (this.victorycount != null && (__self == null || !this.victorycount.equals(__self.victorycount)))
            ret.victorycount = this.victorycount;
        return ret;
    }
    @Override
    public void set(DatabaseRankPlayer value) {
        this.id = value.id;
        this.player_id = value.player_id;
        this.name = value.name;
        this.level = value.level;
        this.feat = value.feat;
        this.victorycount = value.victorycount;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.player_id != null) return false;
        if (this.name != null) return false;
        if (this.level != null) return false;
        if (this.feat != null) return false;
        if (this.victorycount != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.player_id = null;
        this.name = null;
        this.level = null;
        this.feat = null;
        this.victorycount = null;
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
        if (this.feat != null)
            ret += ("feat = " + this.feat.toString() + " ");
        if (this.victorycount != null)
            ret += ("victorycount = " + this.victorycount.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "rankPlayer";
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