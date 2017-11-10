package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Task extends MT_DataBase {
    public static String MD5 = "0b6ffa7e6fc8b14a173676e032b7f7a5";
    private Integer m_nTaskID ;
    /** 任务ID */
    public Integer TaskID() { return m_nTaskID; }
    public Integer getM_nTaskID() {return m_nTaskID; }
    /** 任务ID */
    public Integer ID() { return m_nTaskID; }
    private Boolean m_bIsFirst ;
    /** 是否是任务链第一个任务 */
    public Boolean IsFirst() { return m_bIsFirst; }
    public Boolean getM_bIsFirst() {return m_bIsFirst; }
    private Integer m_nLinkType ;
    /** 任务类型 */
    public Integer LinkType() { return m_nLinkType; }
    public Integer getM_nLinkType() {return m_nLinkType; }
    private Integer m_nLimit ;
    /** 接收任务限制(有个任务限值表，只有任务链第一个任务有效) */
    public Integer Limit() { return m_nLimit; }
    public Integer getM_nLimit() {return m_nLimit; }
    private Integer m_nloop ;
    /** 每天可完成次数上限 */
    public Integer loop() { return m_nloop; }
    public Integer getM_nloop() {return m_nloop; }
    private Integer m_nPriority ;
    /** 任务提示优先级 */
    public Integer Priority() { return m_nPriority; }
    public Integer getM_nPriority() {return m_nPriority; }
    private Integer m_nNextID ;
    /** 后续任务 */
    public Integer NextID() { return m_nNextID; }
    public Integer getM_nNextID() {return m_nNextID; }
    private Integer m_nTeachInstance ;
    /** 触发教学 */
    public Integer TeachInstance() { return m_nTeachInstance; }
    public Integer getM_nTeachInstance() {return m_nTeachInstance; }
    private Integer m_nTriggerGuide ;
    /** 主动触发引导 */
    public Integer TriggerGuide() { return m_nTriggerGuide; }
    public Integer getM_nTriggerGuide() {return m_nTriggerGuide; }
    private ArrayList<Int3> m_arrayAward = new ArrayList<Int3>();
    /** 任务奖励 */
    public ArrayList<Int3> Award() { return m_arrayAward; }
    public ArrayList<Int3> getM_arrayAward() {return m_arrayAward; }
    private String m_sName ;
    /** 任务名称 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private String m_sDescribe ;
    /** 任务描述 */
    public String Describe() { return m_sDescribe; }
    public String getM_sDescribe() {return m_sDescribe; }
    private Integer m_nType ;
    /** 任务类型 */
    public Integer Type() { return m_nType; }
    public Integer getM_nType() {return m_nType; }
    private Integer m_nTargetID ;
    /** 目标ID */
    public Integer TargetID() { return m_nTargetID; }
    public Integer getM_nTargetID() {return m_nTargetID; }
    private Integer m_nTargetNumber ;
    /** 目标数量 */
    public Integer TargetNumber() { return m_nTargetNumber; }
    public Integer getM_nTargetNumber() {return m_nTargetNumber; }
    private Integer m_nTargetArg1 ;
    /** 附加参数1 */
    public Integer TargetArg1() { return m_nTargetArg1; }
    public Integer getM_nTargetArg1() {return m_nTargetArg1; }
    private Integer m_nTargetArg2 ;
    /** 附加参数2 */
    public Integer TargetArg2() { return m_nTargetArg2; }
    public Integer getM_nTargetArg2() {return m_nTargetArg2; }
    private Boolean m_bConsume ;
    /** 如果是收集物品是否消耗收集的物品 */
    public Boolean Consume() { return m_bConsume; }
    public Boolean getM_bConsume() {return m_bConsume; }
    public static MT_Data_Task ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Task Data = new MT_Data_Task();

        Integer count;
		Data.m_nTaskID = reader.getInt();
		Data.m_bIsFirst = (reader.get() == 1 ? true : false);
		Data.m_nLinkType = reader.getInt();
		Data.m_nLimit = reader.getInt();
		Data.m_nloop = reader.getInt();
		Data.m_nPriority = reader.getInt();
		Data.m_nNextID = reader.getInt();
		Data.m_nTeachInstance = reader.getInt();
		Data.m_nTriggerGuide = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayAward.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sDescribe = TableUtil.ReadLString(reader, fileName,"Describe",Data.ID());
		Data.m_nType = reader.getInt();
		Data.m_nTargetID = reader.getInt();
		Data.m_nTargetNumber = reader.getInt();
		Data.m_nTargetArg1 = reader.getInt();
		Data.m_nTargetArg2 = reader.getInt();
		Data.m_bConsume = (reader.get() == 1 ? true : false);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nTaskID)) return false;
        if (!TableUtil.IsInvalid(this.m_bIsFirst)) return false;
        if (!TableUtil.IsInvalid(this.m_nLinkType)) return false;
        if (!TableUtil.IsInvalid(this.m_nLimit)) return false;
        if (!TableUtil.IsInvalid(this.m_nloop)) return false;
        if (!TableUtil.IsInvalid(this.m_nPriority)) return false;
        if (!TableUtil.IsInvalid(this.m_nNextID)) return false;
        if (!TableUtil.IsInvalid(this.m_nTeachInstance)) return false;
        if (!TableUtil.IsInvalid(this.m_nTriggerGuide)) return false;
        if (this.m_arrayAward.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sDescribe)) return false;
        if (!TableUtil.IsInvalid(this.m_nType)) return false;
        if (!TableUtil.IsInvalid(this.m_nTargetID)) return false;
        if (!TableUtil.IsInvalid(this.m_nTargetNumber)) return false;
        if (!TableUtil.IsInvalid(this.m_nTargetArg1)) return false;
        if (!TableUtil.IsInvalid(this.m_nTargetArg2)) return false;
        if (!TableUtil.IsInvalid(this.m_bConsume)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("TaskID"))
            return TaskID();
        if (str.equals("IsFirst"))
            return IsFirst();
        if (str.equals("LinkType"))
            return LinkType();
        if (str.equals("Limit"))
            return Limit();
        if (str.equals("loop"))
            return loop();
        if (str.equals("Priority"))
            return Priority();
        if (str.equals("NextID"))
            return NextID();
        if (str.equals("TeachInstance"))
            return TeachInstance();
        if (str.equals("TriggerGuide"))
            return TriggerGuide();
        if (str.equals("Award"))
            return Award();
        if (str.equals("Name"))
            return Name();
        if (str.equals("Describe"))
            return Describe();
        if (str.equals("Type"))
            return Type();
        if (str.equals("TargetID"))
            return TargetID();
        if (str.equals("TargetNumber"))
            return TargetNumber();
        if (str.equals("TargetArg1"))
            return TargetArg1();
        if (str.equals("TargetArg2"))
            return TargetArg2();
        if (str.equals("Consume"))
            return Consume();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("TaskID =" + TaskID() + '\n');
        str += ("IsFirst =" + IsFirst() + '\n');
        str += ("LinkType =" + LinkType() + '\n');
        str += ("Limit =" + Limit() + '\n');
        str += ("loop =" + loop() + '\n');
        str += ("Priority =" + Priority() + '\n');
        str += ("NextID =" + NextID() + '\n');
        str += ("TeachInstance =" + TeachInstance() + '\n');
        str += ("TriggerGuide =" + TriggerGuide() + '\n');
        str += ("Award =" + Award() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Describe =" + Describe() + '\n');
        str += ("Type =" + Type() + '\n');
        str += ("TargetID =" + TargetID() + '\n');
        str += ("TargetNumber =" + TargetNumber() + '\n');
        str += ("TargetArg1 =" + TargetArg1() + '\n');
        str += ("TargetArg2 =" + TargetArg2() + '\n');
        str += ("Consume =" + Consume() + '\n');
        return str;
    }
}