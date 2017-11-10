package gameserver.utils;

public class PassiveGain {
	public static PassiveGain default_gain = new PassiveGain();
	
	public int value = 0;
	public float value_f = 0;
	public PassiveGain()
	{
		this.value = 0;
		this.value_f = 0;
	}
	public PassiveGain(int value,float value_f)
	{
		this.value = value;
		this.value_f = value_f;
	}
}
