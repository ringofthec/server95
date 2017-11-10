package gameserver.fighting.stats;

public enum INSERT_CORPS_ERROR {
	/// <summary>
    /// 没有错误
    /// </summary>
    NONE,
    /// <summary>
    /// 英雄添加失败（重复添加或英雄不存在）
    /// </summary>
    HERO_ERROR,
    /// <summary>
    /// 士兵添加失败（数量不足或兵种不存在）
    /// </summary>
    CORPS_ERROR,
}
