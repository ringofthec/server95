// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomLegionMeber implements DatabaseFieldDataBase<CustomLegionMeber>,Serializable {
    /** 军团成员ID */
    public long id;
    public long getId() { return id; }
    
    /** 本周贡献 */
    public int week_contribution;
    public int getWeek_contribution() { return week_contribution; }
    
    /** 总贡献 */
    public int meber_total_contribution;
    public int getMeber_total_contribution() { return meber_total_contribution; }
    
    public CustomLegionMeber(){}
    public CustomLegionMeber(long id,int week_contribution,int meber_total_contribution) {
        this.id = id;
        this.week_contribution = week_contribution;
        this.meber_total_contribution = meber_total_contribution;
    }
    @Override
    public void set(CustomLegionMeber other){
        this.id = other.id;
        this.week_contribution = other.week_contribution;
        this.meber_total_contribution = other.meber_total_contribution;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomLegionMeber other = (CustomLegionMeber) obj;
        if (this.id != other.id) return false;
        if (this.week_contribution != other.week_contribution) return false;
        if (this.meber_total_contribution != other.meber_total_contribution) return false;
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