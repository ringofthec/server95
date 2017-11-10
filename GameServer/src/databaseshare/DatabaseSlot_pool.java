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
public class DatabaseSlot_pool implements DatabaseTableDataBase<DatabaseSlot_pool>,Serializable {
    public static final String TableName = "slot_pool";
    /**  */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer id = null;
    public Integer getId() {return id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "game_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer game_id = null;
    public Integer getGame_id() {return game_id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "level_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level_id = null;
    public Integer getLevel_id() {return level_id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "coin",fieldType = Long.class,arrayType = Byte.class)
    public Long coin = null;
    public Long getCoin() {return coin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "reward_coin",fieldType = Long.class,arrayType = Byte.class)
    public Long reward_coin = null;
    public Long getReward_coin() {return reward_coin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "full_coin",fieldType = Long.class,arrayType = Byte.class)
    public Long full_coin = null;
    public Long getFull_coin() {return full_coin;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseSlot_pool __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseSlot_pool();
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
    public DatabaseSlot_pool diff()
    {
        DatabaseSlot_pool ret = new DatabaseSlot_pool();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.game_id != null && (__self == null || !this.game_id.equals(__self.game_id)))
            ret.game_id = this.game_id;
        if (this.level_id != null && (__self == null || !this.level_id.equals(__self.level_id)))
            ret.level_id = this.level_id;
        if (this.coin != null && (__self == null || !this.coin.equals(__self.coin)))
            ret.coin = this.coin;
        if (this.reward_coin != null && (__self == null || !this.reward_coin.equals(__self.reward_coin)))
            ret.reward_coin = this.reward_coin;
        if (this.full_coin != null && (__self == null || !this.full_coin.equals(__self.full_coin)))
            ret.full_coin = this.full_coin;
        return ret;
    }
    @Override
    public void set(DatabaseSlot_pool value) {
        this.id = value.id;
        this.game_id = value.game_id;
        this.level_id = value.level_id;
        this.coin = value.coin;
        this.reward_coin = value.reward_coin;
        this.full_coin = value.full_coin;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.game_id != null) return false;
        if (this.level_id != null) return false;
        if (this.coin != null) return false;
        if (this.reward_coin != null) return false;
        if (this.full_coin != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.game_id = null;
        this.level_id = null;
        this.coin = null;
        this.reward_coin = null;
        this.full_coin = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.game_id != null)
            ret += ("game_id = " + this.game_id.toString() + " ");
        if (this.level_id != null)
            ret += ("level_id = " + this.level_id.toString() + " ");
        if (this.coin != null)
            ret += ("coin = " + this.coin.toString() + " ");
        if (this.reward_coin != null)
            ret += ("reward_coin = " + this.reward_coin.toString() + " ");
        if (this.full_coin != null)
            ret += ("full_coin = " + this.full_coin.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "slot_pool";
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