package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Guide extends MT_DataBase {
    public static String MD5 = "62413e79df413fae81ed7d9ff5fbb1ce";
    private Integer m_nGuideID ;
    /** 引导ID */
    public Integer GuideID() { return m_nGuideID; }
    public Integer getM_nGuideID() {return m_nGuideID; }
    /** 引导ID */
    public Integer ID() { return m_nGuideID; }
    private Boolean m_bIsFirst ;
    /** 是否是第一个引导链 */
    public Boolean IsFirst() { return m_bIsFirst; }
    public Boolean getM_bIsFirst() {return m_bIsFirst; }
    private Integer m_nNextID ;
    /** 后续引导 */
    public Integer NextID() { return m_nNextID; }
    public Integer getM_nNextID() {return m_nNextID; }
    private Integer m_nLimit ;
    /** 开启引导条件 */
    public Integer Limit() { return m_nLimit; }
    public Integer getM_nLimit() {return m_nLimit; }
    private String m_sSkip ;
    /** 跳过引导条件 */
    public String Skip() { return m_sSkip; }
    public String getM_sSkip() {return m_sSkip; }
    private Integer m_nClickSkip ;
    /** 点击是否跳过 */
    public Integer ClickSkip() { return m_nClickSkip; }
    public Integer getM_nClickSkip() {return m_nClickSkip; }
    private Integer m_nType ;
    /** 引导类型 */
    public Integer Type() { return m_nType; }
    public Integer getM_nType() {return m_nType; }
    private Integer m_nArrow ;
    /** 如果是箭头引导，箭头的位置 */
    public Integer Arrow() { return m_nArrow; }
    public Integer getM_nArrow() {return m_nArrow; }
    private Float3 m_ArrowOffset ;
    /** 箭头的偏移 */
    public Float3 ArrowOffset() { return m_ArrowOffset; }
    public Float3 getM_ArrowOffset() {return m_ArrowOffset; }
    private Float3 m_ArrowAngle ;
    /** 箭头的角度 */
    public Float3 ArrowAngle() { return m_ArrowAngle; }
    public Float3 getM_ArrowAngle() {return m_ArrowAngle; }
    private Float3 m_ArrowScale ;
    /** 箭头的缩放 */
    public Float3 ArrowScale() { return m_ArrowScale; }
    public Float3 getM_ArrowScale() {return m_ArrowScale; }
    private String m_sText ;
    /** 引导文字 */
    public String Text() { return m_sText; }
    public String getM_sText() {return m_sText; }
    private Integer m_nInterfaceType ;
    /** 指导界面类型（建筑还是UI）0 UI 1建筑 */
    public Integer InterfaceType() { return m_nInterfaceType; }
    public Integer getM_nInterfaceType() {return m_nInterfaceType; }
    private Integer m_nBuildID ;
    /** 建筑ID（BuildID）-1指正在建造的建筑 */
    public Integer BuildID() { return m_nBuildID; }
    public Integer getM_nBuildID() {return m_nBuildID; }
    private String m_sLevel ;
    /** 引导的场景 */
    public String Level() { return m_sLevel; }
    public String getM_sLevel() {return m_sLevel; }
    private String m_sUI ;
    /** 引导界面 */
    public String UI() { return m_sUI; }
    public String getM_sUI() {return m_sUI; }
    private ArrayList<String> m_arrayExceptUI = new ArrayList<String>();
    /** 不关闭的界面 */
    public ArrayList<String> ExceptUI() { return m_arrayExceptUI; }
    public ArrayList<String> getM_arrayExceptUI() {return m_arrayExceptUI; }
    private Boolean m_bEventListener ;
    /** 是否默认执行指向Button的事件 */
    public Boolean EventListener() { return m_bEventListener; }
    public Boolean getM_bEventListener() {return m_bEventListener; }
    private Boolean m_bMainMenu ;
    /** 是不是主界面缩进按钮 */
    public Boolean MainMenu() { return m_bMainMenu; }
    public Boolean getM_bMainMenu() {return m_bMainMenu; }
    private String m_sButton ;
    /** 引导的按钮 */
    public String Button() { return m_sButton; }
    public String getM_sButton() {return m_sButton; }
    private String m_sEventButton ;
    /** 事件按钮 */
    public String EventButton() { return m_sEventButton; }
    public String getM_sEventButton() {return m_sEventButton; }
    private Float2 m_DragAmount ;
    /** 如果是拖拽的Button 拖拽显示的区域 */
    public Float2 DragAmount() { return m_DragAmount; }
    public Float2 getM_DragAmount() {return m_DragAmount; }
    private Integer m_nContinueEvent ;
    /** 触发该引导需要的事件 */
    public Integer ContinueEvent() { return m_nContinueEvent; }
    public Integer getM_nContinueEvent() {return m_nContinueEvent; }
    private Int4 m_Region ;
    /** 引导区域 */
    public Int4 Region() { return m_Region; }
    public Int4 getM_Region() {return m_Region; }
    private Int4 m_TargetRegion ;
    /** 目标区域 */
    public Int4 TargetRegion() { return m_TargetRegion; }
    public Int4 getM_TargetRegion() {return m_TargetRegion; }
    private Float2 m_DragPosition ;
    /** 如果是拖拽操作，提示文字的位置 */
    public Float2 DragPosition() { return m_DragPosition; }
    public Float2 getM_DragPosition() {return m_DragPosition; }
    private String m_sScripts ;
    /** 点击执行脚本 */
    public String Scripts() { return m_sScripts; }
    public String getM_sScripts() {return m_sScripts; }
    private String m_sUMEvent ;
    /** 触发友盟记录事件 */
    public String UMEvent() { return m_sUMEvent; }
    public String getM_sUMEvent() {return m_sUMEvent; }
    public static MT_Data_Guide ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Guide Data = new MT_Data_Guide();

        Integer count;
		Data.m_nGuideID = reader.getInt();
		Data.m_bIsFirst = (reader.get() == 1 ? true : false);
		Data.m_nNextID = reader.getInt();
		Data.m_nLimit = reader.getInt();
		Data.m_sSkip = TableUtil.ReadString(reader);
		Data.m_nClickSkip = reader.getInt();
		Data.m_nType = reader.getInt();
		Data.m_nArrow = reader.getInt();
		Data.m_ArrowOffset = Float3.ReadMemory(reader, fileName);
		Data.m_ArrowAngle = Float3.ReadMemory(reader, fileName);
		Data.m_ArrowScale = Float3.ReadMemory(reader, fileName);
		Data.m_sText = TableUtil.ReadLString(reader, fileName,"Text",Data.ID());
		Data.m_nInterfaceType = reader.getInt();
		Data.m_nBuildID = reader.getInt();
		Data.m_sLevel = TableUtil.ReadString(reader);
		Data.m_sUI = TableUtil.ReadString(reader);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayExceptUI.add(TableUtil.ReadString(reader));
        }

		Data.m_bEventListener = (reader.get() == 1 ? true : false);
		Data.m_bMainMenu = (reader.get() == 1 ? true : false);
		Data.m_sButton = TableUtil.ReadString(reader);
		Data.m_sEventButton = TableUtil.ReadString(reader);
		Data.m_DragAmount = Float2.ReadMemory(reader, fileName);
		Data.m_nContinueEvent = reader.getInt();
		Data.m_Region = Int4.ReadMemory(reader, fileName);
		Data.m_TargetRegion = Int4.ReadMemory(reader, fileName);
		Data.m_DragPosition = Float2.ReadMemory(reader, fileName);
		Data.m_sScripts = TableUtil.ReadString(reader);
		Data.m_sUMEvent = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nGuideID)) return false;
        if (!TableUtil.IsInvalid(this.m_bIsFirst)) return false;
        if (!TableUtil.IsInvalid(this.m_nNextID)) return false;
        if (!TableUtil.IsInvalid(this.m_nLimit)) return false;
        if (!TableUtil.IsInvalid(this.m_sSkip)) return false;
        if (!TableUtil.IsInvalid(this.m_nClickSkip)) return false;
        if (!TableUtil.IsInvalid(this.m_nType)) return false;
        if (!TableUtil.IsInvalid(this.m_nArrow)) return false;
        if (!TableUtil.IsInvalid(this.m_ArrowOffset)) return false;
        if (!TableUtil.IsInvalid(this.m_ArrowAngle)) return false;
        if (!TableUtil.IsInvalid(this.m_ArrowScale)) return false;
        if (!TableUtil.IsInvalid(this.m_sText)) return false;
        if (!TableUtil.IsInvalid(this.m_nInterfaceType)) return false;
        if (!TableUtil.IsInvalid(this.m_nBuildID)) return false;
        if (!TableUtil.IsInvalid(this.m_sLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_sUI)) return false;
        if (this.m_arrayExceptUI.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_bEventListener)) return false;
        if (!TableUtil.IsInvalid(this.m_bMainMenu)) return false;
        if (!TableUtil.IsInvalid(this.m_sButton)) return false;
        if (!TableUtil.IsInvalid(this.m_sEventButton)) return false;
        if (!TableUtil.IsInvalid(this.m_DragAmount)) return false;
        if (!TableUtil.IsInvalid(this.m_nContinueEvent)) return false;
        if (!TableUtil.IsInvalid(this.m_Region)) return false;
        if (!TableUtil.IsInvalid(this.m_TargetRegion)) return false;
        if (!TableUtil.IsInvalid(this.m_DragPosition)) return false;
        if (!TableUtil.IsInvalid(this.m_sScripts)) return false;
        if (!TableUtil.IsInvalid(this.m_sUMEvent)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("GuideID"))
            return GuideID();
        if (str.equals("IsFirst"))
            return IsFirst();
        if (str.equals("NextID"))
            return NextID();
        if (str.equals("Limit"))
            return Limit();
        if (str.equals("Skip"))
            return Skip();
        if (str.equals("ClickSkip"))
            return ClickSkip();
        if (str.equals("Type"))
            return Type();
        if (str.equals("Arrow"))
            return Arrow();
        if (str.equals("ArrowOffset"))
            return ArrowOffset();
        if (str.equals("ArrowAngle"))
            return ArrowAngle();
        if (str.equals("ArrowScale"))
            return ArrowScale();
        if (str.equals("Text"))
            return Text();
        if (str.equals("InterfaceType"))
            return InterfaceType();
        if (str.equals("BuildID"))
            return BuildID();
        if (str.equals("Level"))
            return Level();
        if (str.equals("UI"))
            return UI();
        if (str.equals("ExceptUI"))
            return ExceptUI();
        if (str.equals("EventListener"))
            return EventListener();
        if (str.equals("MainMenu"))
            return MainMenu();
        if (str.equals("Button"))
            return Button();
        if (str.equals("EventButton"))
            return EventButton();
        if (str.equals("DragAmount"))
            return DragAmount();
        if (str.equals("ContinueEvent"))
            return ContinueEvent();
        if (str.equals("Region"))
            return Region();
        if (str.equals("TargetRegion"))
            return TargetRegion();
        if (str.equals("DragPosition"))
            return DragPosition();
        if (str.equals("Scripts"))
            return Scripts();
        if (str.equals("UMEvent"))
            return UMEvent();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("GuideID =" + GuideID() + '\n');
        str += ("IsFirst =" + IsFirst() + '\n');
        str += ("NextID =" + NextID() + '\n');
        str += ("Limit =" + Limit() + '\n');
        str += ("Skip =" + Skip() + '\n');
        str += ("ClickSkip =" + ClickSkip() + '\n');
        str += ("Type =" + Type() + '\n');
        str += ("Arrow =" + Arrow() + '\n');
        str += ("ArrowOffset =" + ArrowOffset() + '\n');
        str += ("ArrowAngle =" + ArrowAngle() + '\n');
        str += ("ArrowScale =" + ArrowScale() + '\n');
        str += ("Text =" + Text() + '\n');
        str += ("InterfaceType =" + InterfaceType() + '\n');
        str += ("BuildID =" + BuildID() + '\n');
        str += ("Level =" + Level() + '\n');
        str += ("UI =" + UI() + '\n');
        str += ("ExceptUI =" + ExceptUI() + '\n');
        str += ("EventListener =" + EventListener() + '\n');
        str += ("MainMenu =" + MainMenu() + '\n');
        str += ("Button =" + Button() + '\n');
        str += ("EventButton =" + EventButton() + '\n');
        str += ("DragAmount =" + DragAmount() + '\n');
        str += ("ContinueEvent =" + ContinueEvent() + '\n');
        str += ("Region =" + Region() + '\n');
        str += ("TargetRegion =" + TargetRegion() + '\n');
        str += ("DragPosition =" + DragPosition() + '\n');
        str += ("Scripts =" + Scripts() + '\n');
        str += ("UMEvent =" + UMEvent() + '\n');
        return str;
    }
}