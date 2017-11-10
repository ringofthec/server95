// 本文件是自动生成，不要手动修改
package database.gdl.gameserver;
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
public class DatabasePlayer implements DatabaseTableDataBase<DatabasePlayer>,Serializable {
    public static final String TableName = "player";
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家姓名 */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /** 玩家头像 */
    @DatabaseFieldAttribute(fieldName = "head",fieldType = Integer.class,arrayType = Byte.class)
    public Integer head = null;
    public Integer getHead() {return head;}
    /** 国籍 */
    @DatabaseFieldAttribute(fieldName = "nation",fieldType = String.class,arrayType = Byte.class)
    public String nation = null;
    public String getNation() {return nation;}
    /** 玩家性别0男1女 */
    @DatabaseFieldAttribute(fieldName = "sex",fieldType = Integer.class,arrayType = Byte.class)
    public Integer sex = null;
    public Integer getSex() {return sex;}
    /** 玩家登陆统计 */
    @DatabaseFieldAttribute(fieldName = "total_login_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer total_login_num = null;
    public Integer getTotal_login_num() {return total_login_num;}
    /** 玩家连续登陆统计 */
    @DatabaseFieldAttribute(fieldName = "continue_login_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer continue_login_num = null;
    public Integer getContinue_login_num() {return continue_login_num;}
    /** 玩家连续登陆奖励 */
    @DatabaseFieldAttribute(fieldName = "continue_login_reward",fieldType = Integer.class,arrayType = Byte.class)
    public Integer continue_login_reward = null;
    public Integer getContinue_login_reward() {return continue_login_reward;}
    /** 上次下线时间 */
    @DatabaseFieldAttribute(fieldName = "last_offline_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long last_offline_time = null;
    public Long getLast_offline_time() {return last_offline_time;}
    /** 帐号创建时间 */
    @DatabaseFieldAttribute(fieldName = "create_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long create_time = null;
    public Long getCreate_time() {return create_time;}
    /** 上次刷新时间 */
    @DatabaseFieldAttribute(fieldName = "last_flush_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long last_flush_time = null;
    public Long getLast_flush_time() {return last_flush_time;}
    /** 玩家转轮奖励领取米有 */
    @DatabaseFieldAttribute(fieldName = "rotary_reward_get",fieldType = Integer.class,arrayType = Byte.class)
    public Integer rotary_reward_get = null;
    public Integer getRotary_reward_get() {return rotary_reward_get;}
    /** 自己设置的显示礼物 */
    @DatabaseFieldAttribute(fieldName = "showgift_config",fieldType = String.class,arrayType = CustomGiftShow.class)
    public List<CustomGiftShow> showgift_config = null;
    public List<CustomGiftShow> getShowgift_config() {return showgift_config;}
    /** 已经领取的等级奖励 */
    @DatabaseFieldAttribute(fieldName = "level_rewards",fieldType = String.class,arrayType = CustomInt1Info.class)
    public List<CustomInt1Info> level_rewards = null;
    public List<CustomInt1Info> getLevel_rewards() {return level_rewards;}
    /** 摇奖奖励缓存 */
    @DatabaseFieldAttribute(fieldName = "lotty_reward_coin_idx",fieldType = Integer.class,arrayType = Byte.class)
    public Integer lotty_reward_coin_idx = null;
    public Integer getLotty_reward_coin_idx() {return lotty_reward_coin_idx;}
    /** 摇奖奖励缓存 */
    @DatabaseFieldAttribute(fieldName = "lotty_reward_gold_idx",fieldType = Integer.class,arrayType = Byte.class)
    public Integer lotty_reward_gold_idx = null;
    public Integer getLotty_reward_gold_idx() {return lotty_reward_gold_idx;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabasePlayer __self = null;
    @Override
    public void sync()
    {
        __self = new DatabasePlayer();
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
    public DatabasePlayer diff()
    {
        DatabasePlayer ret = new DatabasePlayer();
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.head != null && (__self == null || !this.head.equals(__self.head)))
            ret.head = this.head;
        if (this.nation != null && (__self == null || !this.nation.equals(__self.nation)))
            ret.nation = this.nation;
        if (this.sex != null && (__self == null || !this.sex.equals(__self.sex)))
            ret.sex = this.sex;
        if (this.total_login_num != null && (__self == null || !this.total_login_num.equals(__self.total_login_num)))
            ret.total_login_num = this.total_login_num;
        if (this.continue_login_num != null && (__self == null || !this.continue_login_num.equals(__self.continue_login_num)))
            ret.continue_login_num = this.continue_login_num;
        if (this.continue_login_reward != null && (__self == null || !this.continue_login_reward.equals(__self.continue_login_reward)))
            ret.continue_login_reward = this.continue_login_reward;
        if (this.last_offline_time != null && (__self == null || !this.last_offline_time.equals(__self.last_offline_time)))
            ret.last_offline_time = this.last_offline_time;
        if (this.create_time != null && (__self == null || !this.create_time.equals(__self.create_time)))
            ret.create_time = this.create_time;
        if (this.last_flush_time != null && (__self == null || !this.last_flush_time.equals(__self.last_flush_time)))
            ret.last_flush_time = this.last_flush_time;
        if (this.rotary_reward_get != null && (__self == null || !this.rotary_reward_get.equals(__self.rotary_reward_get)))
            ret.rotary_reward_get = this.rotary_reward_get;
        if (this.showgift_config != null && (__self == null || !this.showgift_config.equals(__self.showgift_config)))
            ret.showgift_config = this.showgift_config;
        if (this.level_rewards != null && (__self == null || !this.level_rewards.equals(__self.level_rewards)))
            ret.level_rewards = this.level_rewards;
        if (this.lotty_reward_coin_idx != null && (__self == null || !this.lotty_reward_coin_idx.equals(__self.lotty_reward_coin_idx)))
            ret.lotty_reward_coin_idx = this.lotty_reward_coin_idx;
        if (this.lotty_reward_gold_idx != null && (__self == null || !this.lotty_reward_gold_idx.equals(__self.lotty_reward_gold_idx)))
            ret.lotty_reward_gold_idx = this.lotty_reward_gold_idx;
        return ret;
    }
    @Override
    public void set(DatabasePlayer value) {
        this.player_id = value.player_id;
        this.name = value.name;
        this.head = value.head;
        this.nation = value.nation;
        this.sex = value.sex;
        this.total_login_num = value.total_login_num;
        this.continue_login_num = value.continue_login_num;
        this.continue_login_reward = value.continue_login_reward;
        this.last_offline_time = value.last_offline_time;
        this.create_time = value.create_time;
        this.last_flush_time = value.last_flush_time;
        this.rotary_reward_get = value.rotary_reward_get;
        this.showgift_config = Utility.cloneList(value.showgift_config);
        this.level_rewards = Utility.cloneList(value.level_rewards);
        this.lotty_reward_coin_idx = value.lotty_reward_coin_idx;
        this.lotty_reward_gold_idx = value.lotty_reward_gold_idx;
    }
    @Override
    public boolean isEmpty() {
        if (this.player_id != null) return false;
        if (this.name != null) return false;
        if (this.head != null) return false;
        if (this.nation != null) return false;
        if (this.sex != null) return false;
        if (this.total_login_num != null) return false;
        if (this.continue_login_num != null) return false;
        if (this.continue_login_reward != null) return false;
        if (this.last_offline_time != null) return false;
        if (this.create_time != null) return false;
        if (this.last_flush_time != null) return false;
        if (this.rotary_reward_get != null) return false;
        if (this.showgift_config != null) return false;
        if (this.level_rewards != null) return false;
        if (this.lotty_reward_coin_idx != null) return false;
        if (this.lotty_reward_gold_idx != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.player_id = null;
        this.name = null;
        this.head = null;
        this.nation = null;
        this.sex = null;
        this.total_login_num = null;
        this.continue_login_num = null;
        this.continue_login_reward = null;
        this.last_offline_time = null;
        this.create_time = null;
        this.last_flush_time = null;
        this.rotary_reward_get = null;
        this.showgift_config = null;
        this.level_rewards = null;
        this.lotty_reward_coin_idx = null;
        this.lotty_reward_gold_idx = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.head != null)
            ret += ("head = " + this.head.toString() + " ");
        if (this.nation != null)
            ret += ("nation = " + this.nation.toString() + " ");
        if (this.sex != null)
            ret += ("sex = " + this.sex.toString() + " ");
        if (this.total_login_num != null)
            ret += ("total_login_num = " + this.total_login_num.toString() + " ");
        if (this.continue_login_num != null)
            ret += ("continue_login_num = " + this.continue_login_num.toString() + " ");
        if (this.continue_login_reward != null)
            ret += ("continue_login_reward = " + this.continue_login_reward.toString() + " ");
        if (this.last_offline_time != null)
            ret += ("last_offline_time = " + this.last_offline_time.toString() + " ");
        if (this.create_time != null)
            ret += ("create_time = " + this.create_time.toString() + " ");
        if (this.last_flush_time != null)
            ret += ("last_flush_time = " + this.last_flush_time.toString() + " ");
        if (this.rotary_reward_get != null)
            ret += ("rotary_reward_get = " + this.rotary_reward_get.toString() + " ");
        if (this.showgift_config != null)
            ret += ("showgift_config = " + this.showgift_config.toString() + " ");
        if (this.level_rewards != null)
            ret += ("level_rewards = " + this.level_rewards.toString() + " ");
        if (this.lotty_reward_coin_idx != null)
            ret += ("lotty_reward_coin_idx = " + this.lotty_reward_coin_idx.toString() + " ");
        if (this.lotty_reward_gold_idx != null)
            ret += ("lotty_reward_gold_idx = " + this.lotty_reward_gold_idx.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "player";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (showgift_config != null && showgift_config.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : showgift_config) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    showgift_config.remove(data);
		    }
        }
        if (level_rewards != null && level_rewards.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : level_rewards) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    level_rewards.remove(data);
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