package gameserver.fighting;

public class Vector2 {
	public static Vector2 zero()
	{
		return new Vector2(0f, 0f);
	}
	public float x;
	public float y;
	public Vector2()
	{
		x = y = 0;
	}
	public Vector2(float x,float y)
	{
		this.x = x;
		this.y = y;
	}
	public float getMagnitude()
	{
		return (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}
	public static Vector2 Minus(Vector2 a,Vector2 b)
	{
		return new Vector2(a.x - b.x,a.y - b.y);
	}
	public static float Distance(Vector2 a, Vector2 b)
	{
		return Minus(a, b).getMagnitude();
	}
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
}
