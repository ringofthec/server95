// 本文件是自动生成，不要手动修改
package database;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomActiveStateValues implements DatabaseFieldDataBase<CustomActiveStateValues>,Serializable {
    /**  */
    public int value1;
    public int getValue1() { return value1; }
    
    /**  */
    public int value2;
    public int getValue2() { return value2; }
    
    /**  */
    public int value3;
    public int getValue3() { return value3; }
    
    public CustomActiveStateValues(){}
    public CustomActiveStateValues(int value1,int value2,int value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }
    @Override
    public void set(CustomActiveStateValues other){
        this.value1 = other.value1;
        this.value2 = other.value2;
        this.value3 = other.value3;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomActiveStateValues other = (CustomActiveStateValues) obj;
        if (this.value1 != other.value1) return false;
        if (this.value2 != other.value2) return false;
        if (this.value3 != other.value3) return false;
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