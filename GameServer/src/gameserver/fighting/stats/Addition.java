package gameserver.fighting.stats;

public class Addition {
	/// <summary>
	/// 收益
	/// </summary>
	public class Profit
	{
	    /// <summary>
	    /// 收益绝对值
	    /// </summary>
	    public int value = 0;
	    /// <summary>
	    /// 收益百分比
	    /// </summary>
	    public float value_f = 0;
	}
	/// <summary>
    /// 增加的血量
    /// </summary>
    public int hp = 0;
    /// <summary>
    /// 增加的最大血量百分比
    /// </summary>
    public float hp_max_f = 0f;
    /// <summary>
    /// 增加的当前血量百分比
    /// </summary>
    public float hp_cur_f = 0f;

    /// <summary>
    /// 最大血量收益
    /// </summary>
    public Profit maxHp = new Profit();
    /// <summary>
    /// 攻击力收益
    /// </summary>
    public Profit attack = new Profit();
    /// <summary>
    /// 防御力收益
    /// </summary>
    public Profit armor = new Profit();
    /// <summary>
    /// 暴击收益
    /// </summary>
    public Profit critical = new Profit();
    /// <summary>
    /// 闪避收益
    /// </summary>
    public Profit dodge = new Profit();

    /// <summary>
    /// 速度
    /// </summary>
    public float speed = 1;
    /// <summary>
    /// 攻击速度
    /// </summary>
    public float atkSpeed = 1;
    /// <summary>
    /// 经验
    /// </summary>
    public float expadd = 1;
    /// <summary>
    /// 金钱
    /// </summary>
    public float moneyadd = 1;
    /// <summary>
    /// 禁止的行为
    /// </summary>
    public int prohibitAction = 0;
}
