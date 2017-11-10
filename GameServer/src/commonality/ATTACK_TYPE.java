package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 攻击类型 */
public enum ATTACK_TYPE {
    /** (1)普攻 */
    IMPALE(1),
    /** (2)爆炸攻击 */
    BIGBANG(2),
    /** (3)破甲 */
    POWER(3),
    /** (4)英雄攻击 */
    HERO(4),
    /** (5)治疗 */
    TREAT(5),
    /** (6)加速 */
    SPEED(6),
    /** (7)降攻 */
    CUT_ATTACK(7),
    ;
    public static ATTACK_TYPE valueOf(int value) {
        if (value == 1) return IMPALE;
        if (value == 2) return BIGBANG;
        if (value == 3) return POWER;
        if (value == 4) return HERO;
        if (value == 5) return TREAT;
        if (value == 6) return SPEED;
        if (value == 7) return CUT_ATTACK;
        return null;
    }
    int number;
    ATTACK_TYPE(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}