/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package com.cloudo.wms.modules.oauth.services;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.oauth.repositories.OauthApprovalsRepository;
import com.cloudo.wms.modules.oauth.models.OauthApprovals;
import com.cloudo.wms.modules.oauth.models.OauthClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class OauthService extends _BaseService<OauthClientDetails> {

    @Autowired
    private OauthApprovalsRepository oauthApprovalsRepository;

    public boolean isApprovedClient(String clientId, String username) {
        OauthApprovals oa = this.oauthApprovalsRepository.findByUserIdAndClientId(username, clientId);
        if (oa != null && Approval.ApprovalStatus.valueOf(oa.getStatus()) == Approval.ApprovalStatus.APPROVED) {
            return true;
        }
        return false;
    }

    public Collection<OauthApprovals> getApprovalsByUser(String username) {
        return this.oauthApprovalsRepository.findByUserId(username);
    }
}
