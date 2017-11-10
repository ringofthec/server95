package gameserver.connection.attribute;

import gameserver.network.protos.common.ProLegion.LegionMemberType;
import gameserver.utils.DbMgr;

import java.util.Iterator;
import java.util.List;

import database.DatabasePlayer;
import databaseshare.CustomLegionMeber;
import databaseshare.CustomOfficial;
import databaseshare.DatabaseLegion;

public class LegionRepair {

	public static void DBMerge()
	{
		List<DatabaseLegion> legions= DbMgr.getInstance().getShareDB().Select(DatabaseLegion.class, "");
		for(DatabaseLegion legion:legions)
      {
			/*
          List<CustomLegionMeber> members= legion.meber_list; 
          for(CustomLegionMeber member:members)
          {
        	  if(member.id==legion.boss_id)
        		  member.type= LegionMemberType.Boos_VALUE;
        	  if(isOfficical(legion.official_list,member.id))
        		  member.type=LegionMemberType.Officical_VALUE;
        	  if(member.type==0)
        		  member.type=LegionMemberType.MemberType_VALUE;
          }
          legion.official_list.clear();
          DbMgr.getInstance().getShareDB()
			.Update(legion, "legion_id = ? ", legion.legion_id);
			*/
   	     }
   	     
      }	
	
	private static   boolean isOfficical(List<CustomOfficial> officials,long pid)
	{
		   for(CustomOfficial official:officials)
		   {
			   if(pid==official.id)
				   return true;
			 }
		return false;
	}
	
	public static void playerLegionIdRepair()
	{
		List<DatabaseLegion> legions= DbMgr.getInstance().getShareDB().Select(DatabaseLegion.class, "");
		for(DatabaseLegion legion :legions)
		{/*
			Iterator<CustomLegionMeber> members=legion.meber_list.iterator();
			while(members.hasNext())
			{
				CustomLegionMeber member=members.next();
				DatabasePlayer player=DbMgr.getInstance().getDbByPlayerId(member.id).selectFieldFrist(DatabasePlayer.class, "player_id=?", "belong_legion", member.id);
				if(player.belong_legion.intValue()!=legion.legion_id)
					members.remove();
			}
			  DbMgr.getInstance().getShareDB()
				.Update(legion, "legion_id = ? ", legion.legion_id);
				*/
		}
	}
	
}
