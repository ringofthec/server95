// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomChatReport implements DatabaseFieldDataBase<CustomChatReport>,Serializable {
    /** 举报人ID */
    public long id;
    /** 举报时间 */
    public long time;
    public CustomChatReport(){}
    public CustomChatReport(long id,long time) {
        this.id = id;
        this.time = time;
    }
    @Override
    public void set(CustomChatReport other){
        this.id = other.id;
        this.time = other.time;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomChatReport other = (CustomChatReport) obj;
        if (this.id != other.id) return false;
        if (this.time != other.time) return false;
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