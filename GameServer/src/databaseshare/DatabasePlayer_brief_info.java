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
public class DatabasePlayer_brief_info implements DatabaseTableDataBase<DatabasePlayer_brief_info>,Serializable {
    public static final String TableName = "player_brief_info";
    /** 玩家id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家是否在线 */
    @DatabaseFieldAttribute(fieldName = "online",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean online = null;
    public Boolean getOnline() {return online;}
    /** 玩家姓名 */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /** 玩家等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 玩家性别 */
    @DatabaseFieldAttribute(fieldName = "sex",fieldType = Integer.class,arrayType = Byte.class)
    public Integer sex = null;
    public Integer getSex() {return sex;}
    /** 玩家vip等级 */
    @DatabaseFieldAttribute(fieldName = "viplevel",fieldType = Integer.class,arrayType = Byte.class)
    public Integer viplevel = null;
    public Integer getViplevel() {return viplevel;}
    /** 玩家钱币数 */
    @DatabaseFieldAttribute(fieldName = "money",fieldType = Long.class,arrayType = Byte.class)
    public Long money = null;
    public Long getMoney() {return money;}
    /** 玩家金砖数 */
    @DatabaseFieldAttribute(fieldName = "gold",fieldType = Long.class,arrayType = Byte.class)
    public Long gold = null;
    public Long getGold() {return gold;}
    /** 玩家总赢取 */
    @DatabaseFieldAttribute(fieldName = "exp",fieldType = Long.class,arrayType = Byte.class)
    public Long exp = null;
    public Long getExp() {return exp;}
    /** 玩家总人气 */
    @DatabaseFieldAttribute(fieldName = "liked",fieldType = Long.class,arrayType = Byte.class)
    public Long liked = null;
    public Long getLiked() {return liked;}
    /** 玩家头像 */
    @DatabaseFieldAttribute(fieldName = "head",fieldType = Integer.class,arrayType = Byte.class)
    public Integer head = null;
    public Integer getHead() {return head;}
    /** facebook头像URL */
    @DatabaseFieldAttribute(fieldName = "head_url",fieldType = String.class,arrayType = String.class)
    public String head_url = null;
    public String getHead_url() {return head_url;}
    /** 显示礼物 */
    @DatabaseFieldAttribute(fieldName = "showgift",fieldType = String.class,arrayType = CustomGiftShow.class)
    public List<CustomGiftShow> showgift = null;
    public List<CustomGiftShow> getShowgift() {return showgift;}
    /** 签名 */
    @DatabaseFieldAttribute(fieldName = "sign",fieldType = String.class,arrayType = String.class)
    public String sign = null;
    public String getSign() {return sign;}
    /** 最近活跃时间 */
    @DatabaseFieldAttribute(fieldName = "last_active_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long last_active_time = null;
    public Long getLast_active_time() {return last_active_time;}
    /** 排行金币数 */
    @DatabaseFieldAttribute(fieldName = "rank_money",fieldType = Long.class,arrayType = Byte.class)
    public Long rank_money = null;
    public Long getRank_money() {return rank_money;}
    /** 排行等级数 */
    @DatabaseFieldAttribute(fieldName = "rank_level",fieldType = Long.class,arrayType = Byte.class)
    public Long rank_level = null;
    public Long getRank_level() {return rank_level;}
    /** 排行人气数 */
    @DatabaseFieldAttribute(fieldName = "rank_liked",fieldType = Long.class,arrayType = Byte.class)
    public Long rank_liked = null;
    public Long getRank_liked() {return rank_liked;}
    /** 上一日赢取 */
    @DatabaseFieldAttribute(fieldName = "yestday_eran",fieldType = Long.class,arrayType = Byte.class)
    public Long yestday_eran = null;
    public Long getYestday_eran() {return yestday_eran;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabasePlayer_brief_info __self = null;
    @Override
    public void sync()
    {
        __self = new DatabasePlayer_brief_info();
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
    public DatabasePlayer_brief_info diff()
    {
        DatabasePlayer_brief_info ret = new DatabasePlayer_brief_info();
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.online != null && (__self == null || !this.online.equals(__self.online)))
            ret.online = this.online;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.sex != null && (__self == null || !this.sex.equals(__self.sex)))
            ret.sex = this.sex;
        if (this.viplevel != null && (__self == null || !this.viplevel.equals(__self.viplevel)))
            ret.viplevel = this.viplevel;
        if (this.money != null && (__self == null || !this.money.equals(__self.money)))
            ret.money = this.money;
        if (this.gold != null && (__self == null || !this.gold.equals(__self.gold)))
            ret.gold = this.gold;
        if (this.exp != null && (__self == null || !this.exp.equals(__self.exp)))
            ret.exp = this.exp;
        if (this.liked != null && (__self == null || !this.liked.equals(__self.liked)))
            ret.liked = this.liked;
        if (this.head != null && (__self == null || !this.head.equals(__self.head)))
            ret.head = this.head;
        if (this.head_url != null && (__self == null || !this.head_url.equals(__self.head_url)))
            ret.head_url = this.head_url;
        if (this.showgift != null && (__self == null || !this.showgift.equals(__self.showgift)))
            ret.showgift = this.showgift;
        if (this.sign != null && (__self == null || !this.sign.equals(__self.sign)))
            ret.sign = this.sign;
        if (this.last_active_time != null && (__self == null || !this.last_active_time.equals(__self.last_active_time)))
            ret.last_active_time = this.last_active_time;
        if (this.rank_money != null && (__self == null || !this.rank_money.equals(__self.rank_money)))
            ret.rank_money = this.rank_money;
        if (this.rank_level != null && (__self == null || !this.rank_level.equals(__self.rank_level)))
            ret.rank_level = this.rank_level;
        if (this.rank_liked != null && (__self == null || !this.rank_liked.equals(__self.rank_liked)))
            ret.rank_liked = this.rank_liked;
        if (this.yestday_eran != null && (__self == null || !this.yestday_eran.equals(__self.yestday_eran)))
            ret.yestday_eran = this.yestday_eran;
        return ret;
    }
    @Override
    public void set(DatabasePlayer_brief_info value) {
        this.player_id = value.player_id;
        this.online = value.online;
        this.name = value.name;
        this.level = value.level;
        this.sex = value.sex;
        this.viplevel = value.viplevel;
        this.money = value.money;
        this.gold = value.gold;
        this.exp = value.exp;
        this.liked = value.liked;
        this.head = value.head;
        this.head_url = value.head_url;
        this.showgift = Utility.cloneList(value.showgift);
        this.sign = value.sign;
        this.last_active_time = value.last_active_time;
        this.rank_money = value.rank_money;
        this.rank_level = value.rank_level;
        this.rank_liked = value.rank_liked;
        this.yestday_eran = value.yestday_eran;
    }
    @Override
    public boolean isEmpty() {
        if (this.player_id != null) return false;
        if (this.online != null) return false;
        if (this.name != null) return false;
        if (this.level != null) return false;
        if (this.sex != null) return false;
        if (this.viplevel != null) return false;
        if (this.money != null) return false;
        if (this.gold != null) return false;
        if (this.exp != null) return false;
        if (this.liked != null) return false;
        if (this.head != null) return false;
        if (this.head_url != null) return false;
        if (this.showgift != null) return false;
        if (this.sign != null) return false;
        if (this.last_active_time != null) return false;
        if (this.rank_money != null) return false;
        if (this.rank_level != null) return false;
        if (this.rank_liked != null) return false;
        if (this.yestday_eran != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.player_id = null;
        this.online = null;
        this.name = null;
        this.level = null;
        this.sex = null;
        this.viplevel = null;
        this.money = null;
        this.gold = null;
        this.exp = null;
        this.liked = null;
        this.head = null;
        this.head_url = null;
        this.showgift = null;
        this.sign = null;
        this.last_active_time = null;
        this.rank_money = null;
        this.rank_level = null;
        this.rank_liked = null;
        this.yestday_eran = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.online != null)
            ret += ("online = " + this.online.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        if (this.sex != null)
            ret += ("sex = " + this.sex.toString() + " ");
        if (this.viplevel != null)
            ret += ("viplevel = " + this.viplevel.toString() + " ");
        if (this.money != null)
            ret += ("money = " + this.money.toString() + " ");
        if (this.gold != null)
            ret += ("gold = " + this.gold.toString() + " ");
        if (this.exp != null)
            ret += ("exp = " + this.exp.toString() + " ");
        if (this.liked != null)
            ret += ("liked = " + this.liked.toString() + " ");
        if (this.head != null)
            ret += ("head = " + this.head.toString() + " ");
        if (this.head_url != null)
            ret += ("head_url = " + this.head_url.toString() + " ");
        if (this.showgift != null)
            ret += ("showgift = " + this.showgift.toString() + " ");
        if (this.sign != null)
            ret += ("sign = " + this.sign.toString() + " ");
        if (this.last_active_time != null)
            ret += ("last_active_time = " + this.last_active_time.toString() + " ");
        if (this.rank_money != null)
            ret += ("rank_money = " + this.rank_money.toString() + " ");
        if (this.rank_level != null)
            ret += ("rank_level = " + this.rank_level.toString() + " ");
        if (this.rank_liked != null)
            ret += ("rank_liked = " + this.rank_liked.toString() + " ");
        if (this.yestday_eran != null)
            ret += ("yestday_eran = " + this.yestday_eran.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "player_brief_info";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (showgift != null && showgift.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : showgift) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    showgift.remove(data);
		    }
        }
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