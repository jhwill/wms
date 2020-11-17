package com.cloudo.wms.modules.core;

import org.springframework.stereotype.Component;

@Component("coreSetup")
public class Setup {
    public String[] MODULES_EXCLUDED = {"core-userProfile"};
}
