// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomPVPActivityGroupPlayerInfo implements DatabaseFieldDataBase<CustomPVPActivityGroupPlayerInfo>,Serializable {
    /** 玩家ID */
    public long playerId;
    public long getPlayerId() { return playerId; }
    
    /** 名字 */
    public String name;
    public String getName() { return name; }
    
    /** 积分 */
    public int point;
    public int getPoint() { return point; }
    
    /** 头像 */
    public int head;
    public int getHead() { return head; }
    
    /** 头像 */
    public String headUrl;
    public String getHeadUrl() { return headUrl; }
    
    public CustomPVPActivityGroupPlayerInfo(){}
    public CustomPVPActivityGroupPlayerInfo(long playerId,String name,int point,int head,String headUrl) {
        this.playerId = playerId;
        this.name = name;
        this.point = point;
        this.head = head;
        this.headUrl = headUrl;
    }
    @Override
    public void set(CustomPVPActivityGroupPlayerInfo other){
        this.playerId = other.playerId;
        this.name = other.name;
        this.point = other.point;
        this.head = other.head;
        this.headUrl = other.headUrl;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomPVPActivityGroupPlayerInfo other = (CustomPVPActivityGroupPlayerInfo) obj;
        if (this.playerId != other.playerId) return false;
        if (!this.name.equals(other.name)) return false;
        if (this.point != other.point) return false;
        if (this.head != other.head) return false;
        if (!this.headUrl.equals(other.headUrl)) return false;
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