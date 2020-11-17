/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package com.cloudo.wms.exceptions;

import com.cloudo.wms.lib.ResponseResult;
import org.springframework.http.HttpStatus;

public class ApiError extends ResponseResult<Exception> {

	public ApiError(Exception data) {
		super(data);
		this.setStatus(HttpStatus.BAD_REQUEST);
		this.setMessage(data.getMessage());
	}
}
