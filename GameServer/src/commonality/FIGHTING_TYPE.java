package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 战斗类型 */
public enum FIGHTING_TYPE {
    /** (0)PVE 副本 */
    PVE(0),
    /** (1)PVP */
    PVP(1),
    /** (2)布阵界面 */
    FORMATION(2),
    /** (3)军团战布阵界面 */
    FORMATION_LEGION_WAR(3),
    /** (4)录像 */
    VIDEO(4),
    /** (5)扫荡 */
    FAST_FIGHTING(5),
    /** (6)第一场战斗 */
    FIRST_FIGHTING(6),
    /** (7)教学战斗 */
    TEACH(7),
    /** (8)英雄之路 */
    PATH_OF_HERO(8),
    /** (9)悬赏 */
    WANTED(9),
    /** (10)军团战 */
    LEGION_WAR(10),
    /** (11)国王争夺战 */
    KING_WAR(11),
    ;
    public static FIGHTING_TYPE valueOf(int value) {
        if (value == 0) return PVE;
        if (value == 1) return PVP;
        if (value == 2) return FORMATION;
        if (value == 3) return FORMATION_LEGION_WAR;
        if (value == 4) return VIDEO;
        if (value == 5) return FAST_FIGHTING;
        if (value == 6) return FIRST_FIGHTING;
        if (value == 7) return TEACH;
        if (value == 8) return PATH_OF_HERO;
        if (value == 9) return WANTED;
        if (value == 10) return LEGION_WAR;
        if (value == 11) return KING_WAR;
        return null;
    }
    int number;
    FIGHTING_TYPE(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}