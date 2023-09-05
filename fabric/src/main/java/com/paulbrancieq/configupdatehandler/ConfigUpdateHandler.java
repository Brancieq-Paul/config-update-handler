package com.paulbrancieq.configupdatehandler;

import com.paulbrancieq.configupdatehandler.old.ConfigUpdateHandlerCommon;
import com.paulbrancieq.configupdatehandler.old.Constants;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class ConfigUpdateHandler implements PreLaunchEntrypoint {
    
    @Override
    public void onPreLaunch() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        ConfigUpdateHandlerCommon.init();
    }
}
