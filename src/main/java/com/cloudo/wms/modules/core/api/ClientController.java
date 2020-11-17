/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package com.cloudo.wms.modules.core.api;

import io.swagger.annotations.ApiOperation;
import com.cloudo.wms.modules.oauth.models.OauthApprovals;
import com.cloudo.wms.modules.oauth.models.OauthClientDetails;
import com.cloudo.wms.modules.oauth.services.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import com.cloudo.wms.lib._BaseApi;
import com.cloudo.wms.modules.core.models.Client;
import com.cloudo.wms.modules.core.services.ClientService;

import java.util.Collection;

@Api(value = "Registered Applications API")
@RestController
@RequestMapping({"/api/core/clients", "/api/v1/core/clients"})
public class ClientController extends _BaseApi<Client> {

	@Autowired
	private ClientService clientService;
	@Autowired
	private OauthService oauthService;

	@Override
	public Client post(@RequestBody Client entity) {
		if (entity.getDetails() == null) {
			entity.setDetails(new OauthClientDetails());
		}
		return this.clientService.saveClient(entity.getId(), entity.getName(), entity.getIcon(),
			entity.getDetails().getWebServerRedirectUri(),
			entity.getDetails().getScope()
		);
	}

	@Override
	public Client put(@PathVariable(name = "id") long id, @RequestBody Client entity) {
		if (entity.getDetails() == null) {
			entity.setDetails(new OauthClientDetails());
		}
		return this.clientService.saveClient(id, entity.getName(), entity.getIcon(),
			entity.getDetails().getWebServerRedirectUri(),
			entity.getDetails().getScope()
		);
	}

	@ApiOperation(value = "Get my approvals")
	@RequestMapping( value= "/myapprovals", method = RequestMethod.GET)
	public Collection<OauthApprovals> getMyClientApprovals() {
		return this.oauthService.getApprovalsByUser(getCurrentUser().getUsername());
	}
}
