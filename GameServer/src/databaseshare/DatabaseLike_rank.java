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
//================等级排行榜===========================
@SuppressWarnings("unused")
public class DatabaseLike_rank implements DatabaseTableDataBase<DatabaseLike_rank>,Serializable {
    public static final String TableName = "like_rank";
    /**  */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "change",fieldType = Integer.class,arrayType = Byte.class)
    public Integer change = null;
    public Integer getChange() {return change;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "url",fieldType = String.class,arrayType = Byte.class)
    public String url = null;
    public String getUrl() {return url;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "exp",fieldType = Long.class,arrayType = Byte.class)
    public Long exp = null;
    public Long getExp() {return exp;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "viplvl",fieldType = Integer.class,arrayType = Byte.class)
    public Integer viplvl = null;
    public Integer getViplvl() {return viplvl;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "coin",fieldType = Long.class,arrayType = Byte.class)
    public Long coin = null;
    public Long getCoin() {return coin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "liked",fieldType = Long.class,arrayType = Byte.class)
    public Long liked = null;
    public Long getLiked() {return liked;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "sex",fieldType = Integer.class,arrayType = Byte.class)
    public Integer sex = null;
    public Integer getSex() {return sex;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseLike_rank __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseLike_rank();
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
    public DatabaseLike_rank diff()
    {
        DatabaseLike_rank ret = new DatabaseLike_rank();
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.change != null && (__self == null || !this.change.equals(__self.change)))
            ret.change = this.change;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.url != null && (__self == null || !this.url.equals(__self.url)))
            ret.url = this.url;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.exp != null && (__self == null || !this.exp.equals(__self.exp)))
            ret.exp = this.exp;
        if (this.viplvl != null && (__self == null || !this.viplvl.equals(__self.viplvl)))
            ret.viplvl = this.viplvl;
        if (this.coin != null && (__self == null || !this.coin.equals(__self.coin)))
            ret.coin = this.coin;
        if (this.liked != null && (__self == null || !this.liked.equals(__self.liked)))
            ret.liked = this.liked;
        if (this.sex != null && (__self == null || !this.sex.equals(__self.sex)))
            ret.sex = this.sex;
        return ret;
    }
    @Override
    public void set(DatabaseLike_rank value) {
        this.player_id = value.player_id;
        this.change = value.change;
        this.name = value.name;
        this.url = value.url;
        this.level = value.level;
        this.exp = value.exp;
        this.viplvl = value.viplvl;
        this.coin = value.coin;
        this.liked = value.liked;
        this.sex = value.sex;
    }
    @Override
    public boolean isEmpty() {
        if (this.player_id != null) return false;
        if (this.change != null) return false;
        if (this.name != null) return false;
        if (this.url != null) return false;
        if (this.level != null) return false;
        if (this.exp != null) return false;
        if (this.viplvl != null) return false;
        if (this.coin != null) return false;
        if (this.liked != null) return false;
        if (this.sex != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.player_id = null;
        this.change = null;
        this.name = null;
        this.url = null;
        this.level = null;
        this.exp = null;
        this.viplvl = null;
        this.coin = null;
        this.liked = null;
        this.sex = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.change != null)
            ret += ("change = " + this.change.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.url != null)
            ret += ("url = " + this.url.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        if (this.exp != null)
            ret += ("exp = " + this.exp.toString() + " ");
        if (this.viplvl != null)
            ret += ("viplvl = " + this.viplvl.toString() + " ");
        if (this.coin != null)
            ret += ("coin = " + this.coin.toString() + " ");
        if (this.liked != null)
            ret += ("liked = " + this.liked.toString() + " ");
        if (this.sex != null)
            ret += ("sex = " + this.sex.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "like_rank";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "player_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return player_id;
    }
}