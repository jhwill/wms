/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package com.cloudo.wms.modules.core.models;

import javax.persistence.*;

import com.cloudo.wms.lib._BaseEntity;
import com.cloudo.wms.modules.oauth.models.OauthClientDetails;

@Entity
@Table(name = "core_client")
public class Client extends _BaseEntity {
	private static final long serialVersionUID = 1L;

	private String name;
	private String icon;

	@OneToOne(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private OauthClientDetails details;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public OauthClientDetails getDetails() {
		return details;
	}

	public void setDetails(OauthClientDetails details) {
		this.details = details;
	}
}
