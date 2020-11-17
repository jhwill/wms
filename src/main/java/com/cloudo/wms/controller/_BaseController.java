/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package com.cloudo.wms.controller;

import com.cloudo.wms.ApplicationContext;
import com.cloudo.wms.config.ApplicationConfig;
import com.cloudo.wms.modules.cms.models.Channel;
import com.cloudo.wms.modules.cms.services.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cloudo.wms.modules.core.models.User;

import java.util.List;

@ControllerAdvice
public abstract class _BaseController {

	@Autowired
	protected ApplicationConfig applicationConfig;
	@Autowired
	protected ChannelService channelService;

	@ModelAttribute("user")
	protected User getCurrentUser() {
		return ApplicationContext.getCurrentUser();
	}

	@ModelAttribute("app")
	protected ApplicationConfig getAppInfo() {
		return this.applicationConfig;
	}

	@ModelAttribute("cms_menus")
	protected List<Channel> cmsChannels() {
		return this.channelService.getTree(false);
	}
}
