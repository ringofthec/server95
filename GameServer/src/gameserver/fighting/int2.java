package gameserver.fighting;

import table.Int2;

public class int2 {
	public int field1 = 0;
    public int field2 = 0;
    public int2() { }
    public int2(int2 val)
    {
        field1 = val.field1;
        field2 = val.field2;
    }
    public int2(Int2 val)
    {
        field1 = val.field1();
        field2 = val.field2();
    }
    public int2(int a, int b)
    {
        field1 = a;
        field2 = b;
    }
}
