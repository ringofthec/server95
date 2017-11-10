package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** VM channel */
public enum VmChannel {
    /** (1)App Store */
    AppStore(1),
    /** (2)Google Play */
    GooglePlay(2),
    /** (3)Amazon */
    Amazon(3),
    /** (4)360 */
    Pay360(4),
    /** (5)Alipay */
    Alipay(5),
    /** (1001)游戏内掉落 */
    InGameDrop(1001),
    /** (1002)从其他虚拟货币具转换而来的 */
    TransformFromOtherVm(1002),
    /** (1003)从其他物品合成的 */
    CompoundFromOtherVm(1003),
    /** (1004)从其他物品分解、转换的 */
    TransformFromOtherItem(1004),
    /** (1005)掉落预留段 1005 - 1999 */
    InGameDropSegmentBegin(1005),
    /** (2001)人为赠送 */
    ManmalGive(2001),
    /** (2002)赠送预留段 2002 - 2999 */
    ManmalGiveSegmentBegin(2002),
    /** (5001)与系统流通 */
    CirculationBetweenSystem(5001),
    /** (5002)与玩家流通 */
    CirculationBetweenPlayer(5002),
    /** (6001)购买商品(服务) */
    PurchaseGoods(6001),
    ;
    public static VmChannel valueOf(int value) {
        if (value == 1) return AppStore;
        if (value == 2) return GooglePlay;
        if (value == 3) return Amazon;
        if (value == 4) return Pay360;
        if (value == 5) return Alipay;
        if (value == 1001) return InGameDrop;
        if (value == 1002) return TransformFromOtherVm;
        if (value == 1003) return CompoundFromOtherVm;
        if (value == 1004) return TransformFromOtherItem;
        if (value == 1005) return InGameDropSegmentBegin;
        if (value == 2001) return ManmalGive;
        if (value == 2002) return ManmalGiveSegmentBegin;
        if (value == 5001) return CirculationBetweenSystem;
        if (value == 5002) return CirculationBetweenPlayer;
        if (value == 6001) return PurchaseGoods;
        return null;
    }
    int number;
    VmChannel(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}