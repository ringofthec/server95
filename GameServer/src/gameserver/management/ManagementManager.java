package gameserver.management;

import gameserver.config.ManagementConfig;

import com.commons.management.ManagementManagerBase;

public class ManagementManager extends ManagementManagerBase {
	private final static ManagementManager instance = new ManagementManager();
    public static ManagementManager getInstance() { return instance; }
    public void initialize()
    {
    	initialize(ManagementConfig.scriptDescriptor);
    }
}
