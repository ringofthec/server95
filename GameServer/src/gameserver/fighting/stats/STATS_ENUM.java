package gameserver.fighting.stats;

public enum STATS_ENUM {
    NONE,
    /// <summary>
    /// 最大生命
    /// </summary>
    @StatsAttribute()
    MAXHP,
    /// <summary>
    /// 当前生命
    /// </summary>
    @StatsAttribute(MaxValue = STATS_ENUM.MAXHP)
    CURHP,
    /// <summary>
    /// 攻击
    /// </summary>
    @StatsAttribute()
    ATTACK,
    /// <summary>
    /// 防御
    /// </summary>
    @StatsAttribute()
    ARMOR,
    /// <summary>
    /// 攻击类型
    /// </summary>
    @StatsAttribute()
    ATTACK_TYPE,
    /// <summary>
    /// 防御类型
    /// </summary>
    @StatsAttribute()
    ARMOR_TYPE,
    /// <summary>
    /// 暴击
    /// </summary>
    @StatsAttribute()
    CRITICAL,
    /// <summary>
    /// 免爆
    /// </summary>
    @StatsAttribute()
    IMMUNECRIT,
    /// <summary>
    /// 命中
    /// </summary>
    @StatsAttribute()
    HIT,
    /// <summary>
    /// 闪避
    /// </summary>
    @StatsAttribute()
    DODGE,
    /// <summary>
    /// 吸血类型, 是具体指还是万分比
    /// </summary>
    @StatsAttribute()
    VAMPIRE_TYPE,
    /// <summary>
    /// 吸血概率
    /// </summary>
    @StatsAttribute()
    VAMPIRE_ODDS,
    /// <summary>
    /// 吸血
    /// </summary>
    @StatsAttribute()
    VAMPIRE,
    /// <summary>
    /// 吸血(徽章或者别的提供的吸血加成, 具体值)
    /// </summary>
    @StatsAttribute()
    VAMPIRE_NUM,
    /// <summary>
    /// 吸血(徽章或者别的提供的吸血加成, 万分比)
    /// </summary>
    @StatsAttribute()
    VAMPIRE_RATIO,
    /// <summary>
    /// 移动速度
    /// </summary>
    @StatsAttribute()
    MOVE_SPEED,
    /// <summary>
    /// 攻击间隔
    /// </summary>
    @StatsAttribute()
    ATTACK_SPEED,
}
