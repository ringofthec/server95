// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomTaskCompleteCount implements DatabaseFieldDataBase<CustomTaskCompleteCount>,Serializable {
    /** 任务id */
    public int taskid;
    /** 完成次数 */
    public int count;
    public CustomTaskCompleteCount(){}
    public CustomTaskCompleteCount(int taskid,int count) {
        this.taskid = taskid;
        this.count = count;
    }
    @Override
    public void set(CustomTaskCompleteCount other){
        this.taskid = other.taskid;
        this.count = other.count;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomTaskCompleteCount other = (CustomTaskCompleteCount) obj;
        if (this.taskid != other.taskid) return false;
        if (this.count != other.count) return false;
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