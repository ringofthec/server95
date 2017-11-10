package gameserver.fighting.stats;

public class HurtResult {
	public boolean Miss;   	//是否MISS
    public boolean Crit;   	//是否暴击
    public Integer Hurt;    	//最终伤害值
    public Integer Vampire; 	//吸血
    public HurtResult()
    {
        this.Miss = false;
        this.Crit = false;
        this.Hurt = 0;
        this.Vampire = 0;
    }
    public HurtResult(boolean Miss, boolean Crit, int Hurt, int Vampire)
    {
        this.Miss = Miss;
        this.Crit = Crit;
        this.Hurt = Hurt;
        this.Vampire = Vampire;
    }
}
