// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomActiveContext implements DatabaseFieldDataBase<CustomActiveContext>,Serializable {
    /**  */
    public String title;
    public String getTitle() { return title; }
    
    /**  */
    public String context;
    public String getContext() { return context; }
    
    public CustomActiveContext(){}
    public CustomActiveContext(String title,String context) {
        this.title = title;
        this.context = context;
    }
    @Override
    public void set(CustomActiveContext other){
        this.title = other.title;
        this.context = other.context;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomActiveContext other = (CustomActiveContext) obj;
        if (!this.title.equals(other.title)) return false;
        if (!this.context.equals(other.context)) return false;
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