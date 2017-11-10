// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomHeroPathCurPoint implements DatabaseFieldDataBase<CustomHeroPathCurPoint>,Serializable {
    /** 当前关卡tableId */
    public int curPointId;
    /** 邀请的英雄数据库Id */
    public int requestHeroId;
    /** 邀请的player_id */
    public long requestPlayerId;
    /** 本方士兵等级 */
    public int cropLv;
    public CustomHeroPathCurPoint(){}
    public CustomHeroPathCurPoint(int curPointId,int requestHeroId,long requestPlayerId,int cropLv) {
        this.curPointId = curPointId;
        this.requestHeroId = requestHeroId;
        this.requestPlayerId = requestPlayerId;
        this.cropLv = cropLv;
    }
    @Override
    public void set(CustomHeroPathCurPoint other){
        this.curPointId = other.curPointId;
        this.requestHeroId = other.requestHeroId;
        this.requestPlayerId = other.requestPlayerId;
        this.cropLv = other.cropLv;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomHeroPathCurPoint other = (CustomHeroPathCurPoint) obj;
        if (this.curPointId != other.curPointId) return false;
        if (this.requestHeroId != other.requestHeroId) return false;
        if (this.requestPlayerId != other.requestPlayerId) return false;
        if (this.cropLv != other.cropLv) return false;
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