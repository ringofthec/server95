package table;
public abstract class MT_TableBase {
    public MT_DataBase GetValue(Integer key) { return null; }
    public MT_DataBase GetValue(String key) { return null; }
    public Boolean Contains(Integer ID) { return false; }
    public Boolean Contains(String ID) { return false; }
    public abstract Integer Count();
}
