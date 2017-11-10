package gameserver.fighting;

import com.commons.util.GridPoint;
import com.commons.util.GridSize;


public class GameTerrain {
    //======游戏格子坐标系========
    //↑
    //丨
    //丨↑
    //丨y
    //丨
    //丨     x →
    //└———————————→
    //======游戏格子坐标系========
    /// <summary>
    /// 获得一个地形
    /// </summary>
    /// <param name="name">地形名字</param>
    /// <param name="origin">左下角坐标</param>
    /// <param name="rows">行数</param>
    /// <param name="lines">列数</param>
    /// <param name="width">一格宽度</param>
    /// <param name="height">一格高度</param>
    /// <returns></returns>
    public static GameTerrain GetTerrain(Vector2 origin, int rows, int lines, float width, float height)
    {
        return new GameTerrain(origin, rows, lines, width, height, true, true);
    }
    private Vector2 origin;				//原点 0，0 点坐标
    private Vector2 margin;				//边缘点坐标
    private int maxX, maxY;				//最大列 行
    private float width, height;		//一个格子的宽高
    private boolean upDown, leftRight;	//坐标上下颠倒 坐标左右颠倒
    protected GameTerrain(Vector2 origin, int maxX, int maxY, float width, float height, boolean upDown, boolean leftRight)
    {
        this.origin = origin;
        this.maxX = maxX;
        this.maxY = maxY;
        this.width = width;
        this.height = height;
        this.upDown = upDown;
        this.leftRight = leftRight;
        this.margin = new Vector2(leftRight ? (origin.x + (maxX * width)) : (origin.x - (maxX * width)), upDown ? (origin.y + (maxY * height)) : (origin.y - (maxY * height)));
    }
    /// <summary>
    /// 获得原点坐标
    /// </summary>
    public Vector2 GetOrigin()
    {
        return origin;
    }
    /// <summary>
    /// 获得边缘坐标
    /// </summary>
    public Vector2 GetMargin()
    {
        return margin;
    }
    /// <summary>
    /// 最大X值
    /// </summary>
    public int GetMaxX()
    {
        return maxX;
    }
    /// <summary>
    /// 最大Y值
    /// </summary>
    public int GetMaxY()
    {
        return maxY;
    }
    /// <summary>
    /// 获得单个格子宽度
    /// </summary>
    public float GetGridWidth()
    {
        return width;
    }
    /// <summary>
    /// 获得单个格子高度
    /// </summary>
    public float GetGridHeight()
    {
        return height;
    }
    //算出左下角坐标
    public GridPoint GetRanksPerfect(Vector2 position, int w, int h)
    {
        position.x -= (w - 1) * GetGridWidth() / 2;
        position.y -= (h - 1) * GetGridHeight() / 2;
        return GetRanksByPosition(position);
    }
    //算出地形坐标
    public GridPoint GetRanksByPosition(Vector2 position)
    {
    	Double x = Math.floor((position.x - origin.x) / width);
    	Double y = Math.floor((position.y - origin.y) / height);
    	return new GridPoint(x.intValue(),y.intValue());
    }
    
    public Vector2 GetPixelPerfect(GridPoint point, GridSize size)
    {
        return GetPixelPerfect(point.x, point.y, size.width, size.height);
    }
	//根据坐标获得放置的中心点  坐标点是地形左下
    public Vector2 GetPixelPerfect(int x, int y, int w, int h)
    {
        Vector2 positionLeftBottom = GetPositionByRanks(x, y, true);
        Vector2 positionRightTop = GetPositionByRanks(x + w - 1, y + h - 1, true);
        return new Vector2((positionLeftBottom.x + positionRightTop.x) / 2, (positionLeftBottom.y + positionRightTop.y) / 2);
    }
    /// <summary>
    /// 返回坐标点是否在本地形内
    /// 返回世界坐标 就算没有在地形内 还是会正确返回
    /// </summary>
    public Vector2 GetPositionByRanks(GridPoint position, boolean centerOffset)
    {
        return GetPositionByRanks(position.x, position.y, centerOffset);
    }
    /// <summary>
    /// 返回坐标点是否在本地形内
    /// 返回世界坐标 就算没有在地形内 还是会正确返回
    /// </summary>
    public Vector2 GetPositionByRanks(int ix, int iy, boolean centerOffset)
    {
        float xOffset = ix * width;
        if (centerOffset) xOffset = leftRight ? xOffset + width / 2 : xOffset - width / 2;
        float yOffset = iy * height;
        if (centerOffset) yOffset = upDown ? yOffset + height / 2 : yOffset - height / 2;
        float ox = leftRight ? origin.x + xOffset : origin.x - xOffset;
        float oy = upDown ? origin.y + yOffset : origin.y - yOffset;
        return new Vector2(ox, oy);
    }
    /// <summary>
    /// 格子是否为有效值
    /// </summary>
    public boolean IsValidRanks(GridPoint pos)
    {
        return IsValidRanks(pos.x, pos.y);
    }
    /// <summary>
    /// 格子是否为有效值
    /// </summary>
    public boolean IsValidRanks(int x, int y)
    {
        return IsValidRanksX(x) && IsValidRanksY(y);
    }
    /// <summary>
    /// 格子X是否为有效值
    /// </summary>
    public boolean IsValidRanksX(int x)
    {
        if (x < 0 || x >= GetMaxX())
            return false;
        return true;
    }
    /// <summary>
    /// 格子Y是否为有效值
    /// </summary>
    public boolean IsValidRanksY(int y)
    {
        if (y < 0 || y >= GetMaxY())
            return false;
        return true;
    }
}
