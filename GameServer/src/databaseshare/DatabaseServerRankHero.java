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
//================英雄表===========================
@SuppressWarnings("unused")
public class DatabaseServerRankHero implements DatabaseTableDataBase<DatabaseServerRankHero>,Serializable {
    public static final String TableName = "serverRankHero";
    /** 本表ID */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer id = null;
    public Integer getId() {return id;}
    /** 英雄唯一ID：多个Gs，在这个表可能会重复 */
    @DatabaseFieldAttribute(fieldName = "hero_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer hero_id = null;
    public Integer getHero_id() {return hero_id;}
    /** 英雄表中ID */
    @DatabaseFieldAttribute(fieldName = "hero_table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer hero_table_id = null;
    public Integer getHero_table_id() {return hero_table_id;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家名称 */
    @DatabaseFieldAttribute(fieldName = "player_name",fieldType = String.class,arrayType = Byte.class)
    public String player_name = null;
    public String getPlayer_name() {return player_name;}
    /** 战斗力 */
    @DatabaseFieldAttribute(fieldName = "fight_val",fieldType = Integer.class,arrayType = Byte.class)
    public Integer fight_val = null;
    public Integer getFight_val() {return fight_val;}
    /** 服务器ID */
    @DatabaseFieldAttribute(fieldName = "server",fieldType = Integer.class,arrayType = Byte.class)
    public Integer server = null;
    public Integer getServer() {return server;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseServerRankHero __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseServerRankHero();
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
    public DatabaseServerRankHero diff()
    {
        DatabaseServerRankHero ret = new DatabaseServerRankHero();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.hero_id != null && (__self == null || !this.hero_id.equals(__self.hero_id)))
            ret.hero_id = this.hero_id;
        if (this.hero_table_id != null && (__self == null || !this.hero_table_id.equals(__self.hero_table_id)))
            ret.hero_table_id = this.hero_table_id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.player_name != null && (__self == null || !this.player_name.equals(__self.player_name)))
            ret.player_name = this.player_name;
        if (this.fight_val != null && (__self == null || !this.fight_val.equals(__self.fight_val)))
            ret.fight_val = this.fight_val;
        if (this.server != null && (__self == null || !this.server.equals(__self.server)))
            ret.server = this.server;
        return ret;
    }
    @Override
    public void set(DatabaseServerRankHero value) {
        this.id = value.id;
        this.hero_id = value.hero_id;
        this.hero_table_id = value.hero_table_id;
        this.player_id = value.player_id;
        this.player_name = value.player_name;
        this.fight_val = value.fight_val;
        this.server = value.server;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.hero_id != null) return false;
        if (this.hero_table_id != null) return false;
        if (this.player_id != null) return false;
        if (this.player_name != null) return false;
        if (this.fight_val != null) return false;
        if (this.server != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.hero_id = null;
        this.hero_table_id = null;
        this.player_id = null;
        this.player_name = null;
        this.fight_val = null;
        this.server = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.hero_id != null)
            ret += ("hero_id = " + this.hero_id.toString() + " ");
        if (this.hero_table_id != null)
            ret += ("hero_table_id = " + this.hero_table_id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.player_name != null)
            ret += ("player_name = " + this.player_name.toString() + " ");
        if (this.fight_val != null)
            ret += ("fight_val = " + this.fight_val.toString() + " ");
        if (this.server != null)
            ret += ("server = " + this.server.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "serverRankHero";
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