package gameserver.connection.attribute.info.build;

import commonality.Common;

import gameserver.connection.attribute.ConBuildAttr;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.network.server.connection.Connection;
import table.MT_Data_Building;
import table.base.TableManager;
import database.DatabaseBuild;

public class BuildInfoFactory {
	public static BuildInfo createBuildInfo(Connection connection,
			ConBuildAttr attribute,DatabaseBuild build) throws Exception {
		MT_Data_Building cof = TableManager.GetInstance().TableBuilding().GetElement(build.table_id);
		
		if (cof.Type().equals(Common.BUILDTYPE.Factory.Number()))
			return new FactoryBuildInfo(connection, attribute, build);
		else if (cof.Type().equals(Common.BUILDTYPE.Institute.Number()))
			return new InstituteBuildInfo(connection, attribute, build);
		else if (cof.Type().equals(Common.BUILDTYPE.Tech.Number()))
			return new TechBuildInfo(connection, attribute, build);
		else if (cof.Type().equals(Common.BUILDTYPE.Assets.Number()))
			return new AssetsBuildInfo(connection, attribute, build);
		
		return new BuildInfo(connection, attribute, build);
	}
}
