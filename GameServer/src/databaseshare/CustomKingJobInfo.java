// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomKingJobInfo implements DatabaseFieldDataBase<CustomKingJobInfo>,Serializable {
    /** 类型 */
    public int type;
    public int getType() { return type; }
    
    /** 名称 */
    public String name;
    public String getName() { return name; }
    
    /** 玩家ID */
    public long playerId;
    public long getPlayerId() { return playerId; }
    
    /** 头像 */
    public int head;
    public int getHead() { return head; }
    
    /** 头像 */
    public String headUrl;
    public String getHeadUrl() { return headUrl; }
    
    /** 最后一次更新时间 */
    public long lastupdatetime;
    public long getLastupdatetime() { return lastupdatetime; }
    
    public CustomKingJobInfo(){}
    public CustomKingJobInfo(int type,String name,long playerId,int head,String headUrl,long lastupdatetime) {
        this.type = type;
        this.name = name;
        this.playerId = playerId;
        this.head = head;
        this.headUrl = headUrl;
        this.lastupdatetime = lastupdatetime;
    }
    @Override
    public void set(CustomKingJobInfo other){
        this.type = other.type;
        this.name = other.name;
        this.playerId = other.playerId;
        this.head = other.head;
        this.headUrl = other.headUrl;
        this.lastupdatetime = other.lastupdatetime;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomKingJobInfo other = (CustomKingJobInfo) obj;
        if (this.type != other.type) return false;
        if (!this.name.equals(other.name)) return false;
        if (this.playerId != other.playerId) return false;
        if (this.head != other.head) return false;
        if (!this.headUrl.equals(other.headUrl)) return false;
        if (this.lastupdatetime != other.lastupdatetime) return false;
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