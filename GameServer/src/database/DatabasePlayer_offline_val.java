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
//================玩家下线记录===========================
@SuppressWarnings("unused")
public class DatabasePlayer_offline_val implements DatabaseTableDataBase<DatabasePlayer_offline_val>,Serializable {
    public static final String TableName = "player_offline_val";
    /** ID */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** player_id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 类型 */
    @DatabaseFieldAttribute(fieldName = "type",fieldType = Integer.class,arrayType = gameserver.network.protos.game.CommonProto.Proto_Off_Line.class)
    public gameserver.network.protos.game.CommonProto.Proto_Off_Line type = null;
    public gameserver.network.protos.game.CommonProto.Proto_Off_Line getType() {return type;}
    /** 下线时刻的值 */
    @DatabaseFieldAttribute(fieldName = "value",fieldType = Integer.class,arrayType = Byte.class)
    public Integer value = null;
    public Integer getValue() {return value;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabasePlayer_offline_val __self = null;
    @Override
    public void sync()
    {
        __self = new DatabasePlayer_offline_val();
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
    public DatabasePlayer_offline_val diff()
    {
        DatabasePlayer_offline_val ret = new DatabasePlayer_offline_val();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.type != null && (__self == null || !this.type.equals(__self.type)))
            ret.type = this.type;
        if (this.value != null && (__self == null || !this.value.equals(__self.value)))
            ret.value = this.value;
        return ret;
    }
    @Override
    public void set(DatabasePlayer_offline_val value) {
        this.id = value.id;
        this.player_id = value.player_id;
        this.type = value.type;
        this.value = value.value;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.player_id != null) return false;
        if (this.type != null) return false;
        if (this.value != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.player_id = null;
        this.type = null;
        this.value = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.type != null)
            ret += ("type = " + this.type.toString() + " ");
        if (this.value != null)
            ret += ("value = " + this.value.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "player_offline_val";
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