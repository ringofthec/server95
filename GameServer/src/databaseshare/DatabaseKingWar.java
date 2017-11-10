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
public class DatabaseKingWar implements DatabaseTableDataBase<DatabaseKingWar>,Serializable {
    public static final String TableName = "kingWar";
    /** 唯一ID */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer id = null;
    public Integer getId() {return id;}
    /** 是否开战 */
    @DatabaseFieldAttribute(fieldName = "isWar",fieldType = Long.class,arrayType = Boolean.class)
    public Boolean isWar = null;
    public Boolean getIsWar() {return isWar;}
    /** 状态开始时间 */
    @DatabaseFieldAttribute(fieldName = "startTime",fieldType = Long.class,arrayType = Byte.class)
    public Long startTime = null;
    public Long getStartTime() {return startTime;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "playerId",fieldType = Long.class,arrayType = Byte.class)
    public Long playerId = null;
    public Long getPlayerId() {return playerId;}
    /** 玩家姓名 */
    @DatabaseFieldAttribute(fieldName = "playerName",fieldType = String.class,arrayType = Byte.class)
    public String playerName = null;
    public String getPlayerName() {return playerName;}
    /** 军团ID */
    @DatabaseFieldAttribute(fieldName = "legionId",fieldType = Integer.class,arrayType = Byte.class)
    public Integer legionId = null;
    public Integer getLegionId() {return legionId;}
    /** 军团名称 */
    @DatabaseFieldAttribute(fieldName = "legionName",fieldType = String.class,arrayType = Byte.class)
    public String legionName = null;
    public String getLegionName() {return legionName;}
    /** 国别 */
    @DatabaseFieldAttribute(fieldName = "nation",fieldType = String.class,arrayType = Byte.class)
    public String nation = null;
    public String getNation() {return nation;}
    /** 防御值 */
    @DatabaseFieldAttribute(fieldName = "defVal",fieldType = Integer.class,arrayType = Byte.class)
    public Integer defVal = null;
    public Integer getDefVal() {return defVal;}
    /** 忠诚度 */
    @DatabaseFieldAttribute(fieldName = "loyaltyVal",fieldType = Integer.class,arrayType = Byte.class)
    public Integer loyaltyVal = null;
    public Integer getLoyaltyVal() {return loyaltyVal;}
    /** 最近一次自动减少防御值时间 */
    @DatabaseFieldAttribute(fieldName = "lastdecdeftime",fieldType = Long.class,arrayType = Byte.class)
    public Long lastdecdeftime = null;
    public Long getLastdecdeftime() {return lastdecdeftime;}
    /** 职位列表 */
    @DatabaseFieldAttribute(fieldName = "jobList",fieldType = String.class,arrayType = CustomKingJobInfo.class)
    public List<CustomKingJobInfo> jobList = null;
    public List<CustomKingJobInfo> getJobList() {return jobList;}
    /** 福利列表 */
    @DatabaseFieldAttribute(fieldName = "welfareList",fieldType = String.class,arrayType = CustomKingWelfareInfo.class)
    public List<CustomKingWelfareInfo> welfareList = null;
    public List<CustomKingWelfareInfo> getWelfareList() {return welfareList;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseKingWar __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseKingWar();
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
    public DatabaseKingWar diff()
    {
        DatabaseKingWar ret = new DatabaseKingWar();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.isWar != null && (__self == null || !this.isWar.equals(__self.isWar)))
            ret.isWar = this.isWar;
        if (this.startTime != null && (__self == null || !this.startTime.equals(__self.startTime)))
            ret.startTime = this.startTime;
        if (this.playerId != null && (__self == null || !this.playerId.equals(__self.playerId)))
            ret.playerId = this.playerId;
        if (this.playerName != null && (__self == null || !this.playerName.equals(__self.playerName)))
            ret.playerName = this.playerName;
        if (this.legionId != null && (__self == null || !this.legionId.equals(__self.legionId)))
            ret.legionId = this.legionId;
        if (this.legionName != null && (__self == null || !this.legionName.equals(__self.legionName)))
            ret.legionName = this.legionName;
        if (this.nation != null && (__self == null || !this.nation.equals(__self.nation)))
            ret.nation = this.nation;
        if (this.defVal != null && (__self == null || !this.defVal.equals(__self.defVal)))
            ret.defVal = this.defVal;
        if (this.loyaltyVal != null && (__self == null || !this.loyaltyVal.equals(__self.loyaltyVal)))
            ret.loyaltyVal = this.loyaltyVal;
        if (this.lastdecdeftime != null && (__self == null || !this.lastdecdeftime.equals(__self.lastdecdeftime)))
            ret.lastdecdeftime = this.lastdecdeftime;
        if (this.jobList != null && (__self == null || !this.jobList.equals(__self.jobList)))
            ret.jobList = this.jobList;
        if (this.welfareList != null && (__self == null || !this.welfareList.equals(__self.welfareList)))
            ret.welfareList = this.welfareList;
        return ret;
    }
    @Override
    public void set(DatabaseKingWar value) {
        this.id = value.id;
        this.isWar = value.isWar;
        this.startTime = value.startTime;
        this.playerId = value.playerId;
        this.playerName = value.playerName;
        this.legionId = value.legionId;
        this.legionName = value.legionName;
        this.nation = value.nation;
        this.defVal = value.defVal;
        this.loyaltyVal = value.loyaltyVal;
        this.lastdecdeftime = value.lastdecdeftime;
        this.jobList = Utility.cloneList(value.jobList);
        this.welfareList = Utility.cloneList(value.welfareList);
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.isWar != null) return false;
        if (this.startTime != null) return false;
        if (this.playerId != null) return false;
        if (this.playerName != null) return false;
        if (this.legionId != null) return false;
        if (this.legionName != null) return false;
        if (this.nation != null) return false;
        if (this.defVal != null) return false;
        if (this.loyaltyVal != null) return false;
        if (this.lastdecdeftime != null) return false;
        if (this.jobList != null) return false;
        if (this.welfareList != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.isWar = null;
        this.startTime = null;
        this.playerId = null;
        this.playerName = null;
        this.legionId = null;
        this.legionName = null;
        this.nation = null;
        this.defVal = null;
        this.loyaltyVal = null;
        this.lastdecdeftime = null;
        this.jobList = null;
        this.welfareList = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.isWar != null)
            ret += ("isWar = " + this.isWar.toString() + " ");
        if (this.startTime != null)
            ret += ("startTime = " + this.startTime.toString() + " ");
        if (this.playerId != null)
            ret += ("playerId = " + this.playerId.toString() + " ");
        if (this.playerName != null)
            ret += ("playerName = " + this.playerName.toString() + " ");
        if (this.legionId != null)
            ret += ("legionId = " + this.legionId.toString() + " ");
        if (this.legionName != null)
            ret += ("legionName = " + this.legionName.toString() + " ");
        if (this.nation != null)
            ret += ("nation = " + this.nation.toString() + " ");
        if (this.defVal != null)
            ret += ("defVal = " + this.defVal.toString() + " ");
        if (this.loyaltyVal != null)
            ret += ("loyaltyVal = " + this.loyaltyVal.toString() + " ");
        if (this.lastdecdeftime != null)
            ret += ("lastdecdeftime = " + this.lastdecdeftime.toString() + " ");
        if (this.jobList != null)
            ret += ("jobList = " + this.jobList.toString() + " ");
        if (this.welfareList != null)
            ret += ("welfareList = " + this.welfareList.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "kingWar";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (jobList != null && jobList.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : jobList) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    jobList.remove(data);
		    }
        }
        if (welfareList != null && welfareList.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : welfareList) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    welfareList.remove(data);
		    }
        }
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