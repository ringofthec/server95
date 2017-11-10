package gameserver.utils;

import table.MT_TableEnum;
import gameserver.network.protos.game.ProHint;

public class TransFormArgs {
	public ProHint.Msg_G2C_Prompt.TRANSFORM_TYPE type = ProHint.Msg_G2C_Prompt.TRANSFORM_TYPE.TABLE;
	public String tablekey = "";
	public String tablename = "";
	public int id=0;
	public String columnkey="";
	
	private TransFormArgs(String tablename, String key, int id) {
		this.tablename = tablename;
		this.columnkey = key;
		this.id = id;
	}
	
	private TransFormArgs(String tablekey, String tablename, String key, int id) {
		this(tablename, key, id);
		this.tablekey = tablekey;
		this.type = ProHint.Msg_G2C_Prompt.TRANSFORM_TYPE.TABLE_WITH_KEY;
	}
	
	public static TransFormArgs CreateItemArgs(int id) {
		return new TransFormArgs("Item", MT_TableEnum.Name, id);
	}
	
	public static TransFormArgs CreateAirArgs(int id) {
		return new TransFormArgs("AirSupport", MT_TableEnum.Name, id);
	}
	
	public static TransFormArgs CreateCorpArgs(int id) {
		return new TransFormArgs("Corps", MT_TableEnum.Name, id);
	}
	
	public static TransFormArgs CreateBuildArgs(String tablekey, String tablename, int id) {
		return new TransFormArgs(tablekey, tablename, MT_TableEnum.Name, id);
	}
	
	public static TransFormArgs CreateBuildArgs(String scriptName, int id) {
		String[] temp = scriptName.split("_");
		return new TransFormArgs(temp[0], temp[1], MT_TableEnum.Name, id);
	}
	
	public static TransFormArgs CreateCommdityArgs(int id) {
		return new TransFormArgs("commodity", MT_TableEnum.Name, id);
	}
}
