package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** Product channel */
public enum ProductChannel {
    /** (1)购买得到 */
    Buy(1),
    /** (1001)游戏内掉落 */
    InGameDrop(1001),
    /** (1003)通过合成得到的 */
    CompoundFromOtherGoods(1003),
    /** (1004)从其他物品分解、转换的 */
    TransformFromOtherItem(1004),
    /** (1005)增加预留段 1005 - 1999 */
    InGameDropSegmentBegin(1005),
    /** (2001)人为赠送 */
    ManmalGive(2001),
    /** (2002)赠送预留段 2002 - 2999 */
    ManmalGiveSegmentBegin(2002),
    /** (5001)与系统流通 */
    CirculationBetweenSystem(5001),
    /** (5002)与玩家流通 */
    CirculationBetweenPlayer(5002),
    /** (6001)消费 */
    PurchaseGoods(6001),
    /** (6002)活动结束后扣除 */
    DelByActivityEnd(6002),
    /** (6003)道具用于合成 */
    DelByCompound(6003),
    /** (6004)道具用于分解，转化 */
    DelByTransform(6004),
    ;
    public static ProductChannel valueOf(int value) {
        if (value == 1) return Buy;
        if (value == 1001) return InGameDrop;
        if (value == 1003) return CompoundFromOtherGoods;
        if (value == 1004) return TransformFromOtherItem;
        if (value == 1005) return InGameDropSegmentBegin;
        if (value == 2001) return ManmalGive;
        if (value == 2002) return ManmalGiveSegmentBegin;
        if (value == 5001) return CirculationBetweenSystem;
        if (value == 5002) return CirculationBetweenPlayer;
        if (value == 6001) return PurchaseGoods;
        if (value == 6002) return DelByActivityEnd;
        if (value == 6003) return DelByCompound;
        if (value == 6004) return DelByTransform;
        return null;
    }
    int number;
    ProductChannel(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}