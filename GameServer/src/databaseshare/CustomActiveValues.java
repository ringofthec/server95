// 本文件是自动生成，不要手动修改
package databaseshare;
import com.commons.database.DatabaseFieldDataBase;
import java.io.Serializable;
public class CustomActiveValues implements DatabaseFieldDataBase<CustomActiveValues>,Serializable {
    /**  */
    public int value1;
    public int getValue1() { return value1; }
    
    /**  */
    public int value2;
    public int getValue2() { return value2; }
    
    /**  */
    public int value3;
    public int getValue3() { return value3; }
    
    /**  */
    public float value4;
    public float getValue4() { return value4; }
    
    /**  */
    public int value5;
    public int getValue5() { return value5; }
    
    public CustomActiveValues(){}
    public CustomActiveValues(int value1,int value2,int value3,float value4,int value5) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
    }
    @Override
    public void set(CustomActiveValues other){
        this.value1 = other.value1;
        this.value2 = other.value2;
        this.value3 = other.value3;
        this.value4 = other.value4;
        this.value5 = other.value5;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
        CustomActiveValues other = (CustomActiveValues) obj;
        if (this.value1 != other.value1) return false;
        if (this.value2 != other.value2) return false;
        if (this.value3 != other.value3) return false;
        if (this.value4 != other.value4) return false;
        if (this.value5 != other.value5) return false;
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