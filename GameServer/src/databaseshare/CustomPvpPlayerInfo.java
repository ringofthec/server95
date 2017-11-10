// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomPvpPlayerInfo implements DatabaseFieldDataBase<CustomPvpPlayerInfo>,Serializable {
    /**  */
    public int index;
    public int getIndex() { return index; }
    
    /**  */
    public long playerId;
    public long getPlayerId() { return playerId; }
    
    /**  */
    public String name;
    public String getName() { return name; }
    
    /**  */
    public int level;
    public int getLevel() { return level; }
    
    /**  */
    public String nation;
    public String getNation() { return nation; }
    
    /**  */
    public int fightVal;
    public int getFightVal() { return fightVal; }
    
    /**  */
    public int feat;
    public int getFeat() { return feat; }
    
    /**  */
    public int head;
    public int getHead() { return head; }
    
    /**  */
    public String headUrl;
    public String getHeadUrl() { return headUrl; }
    
    /**  */
    public String legionName;
    public String getLegionName() { return legionName; }
    
    /**  */
    public int winMoney;
    public int getWinMoney() { return winMoney; }
    
    /**  */
    public int winFeat;
    public int getWinFeat() { return winFeat; }
    
    /**  */
    public int robotIndex;
    public int getRobotIndex() { return robotIndex; }
    
    /**  */
    public long recordTime;
    public long getRecordTime() { return recordTime; }
    
    /**  */
    public long shield_end_time;
    public long getShield_end_time() { return shield_end_time; }
    
    /**  */
    public int pos;
    public int getPos() { return pos; }
    
    public CustomPvpPlayerInfo(){pos = 0;}
    public CustomPvpPlayerInfo(int index,long playerId,String name,int level,String nation,int fightVal,int feat,int head,String headUrl,String legionName,int winMoney,int winFeat,int robotIndex,long recordTime,long shield_end_time,int pos) {
        this.index = index;
        this.playerId = playerId;
        this.name = name;
        this.level = level;
        this.nation = nation;
        this.fightVal = fightVal;
        this.feat = feat;
        this.head = head;
        this.headUrl = headUrl;
        this.legionName = legionName;
        this.winMoney = winMoney;
        this.winFeat = winFeat;
        this.robotIndex = robotIndex;
        this.recordTime = recordTime;
        this.shield_end_time = shield_end_time;
        this.pos = pos;
    }
    @Override
    public void set(CustomPvpPlayerInfo other){
        this.index = other.index;
        this.playerId = other.playerId;
        this.name = other.name;
        this.level = other.level;
        this.nation = other.nation;
        this.fightVal = other.fightVal;
        this.feat = other.feat;
        this.head = other.head;
        this.headUrl = other.headUrl;
        this.legionName = other.legionName;
        this.winMoney = other.winMoney;
        this.winFeat = other.winFeat;
        this.robotIndex = other.robotIndex;
        this.recordTime = other.recordTime;
        this.shield_end_time = other.shield_end_time;
        this.pos = other.pos;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomPvpPlayerInfo other = (CustomPvpPlayerInfo) obj;
        if (this.index != other.index) return false;
        if (this.playerId != other.playerId) return false;
        if (!this.name.equals(other.name)) return false;
        if (this.level != other.level) return false;
        if (!this.nation.equals(other.nation)) return false;
        if (this.fightVal != other.fightVal) return false;
        if (this.feat != other.feat) return false;
        if (this.head != other.head) return false;
        if (!this.headUrl.equals(other.headUrl)) return false;
        if (!this.legionName.equals(other.legionName)) return false;
        if (this.winMoney != other.winMoney) return false;
        if (this.winFeat != other.winFeat) return false;
        if (this.robotIndex != other.robotIndex) return false;
        if (this.recordTime != other.recordTime) return false;
        if (this.shield_end_time != other.shield_end_time) return false;
        if (this.pos != other.pos) return false;
        return true;
    }
    @Override
    public int hashCode() { 
        return 0; 
    }
    @Override
    public boolean isValid() {
        return true;
    }
}