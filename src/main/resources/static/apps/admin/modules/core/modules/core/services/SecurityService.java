/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package com.cloudo.wms.modules.core.services;

public interface SecurityService {
	
	String findLoggedInUsername();
	void autologin(String username, String password);
}
