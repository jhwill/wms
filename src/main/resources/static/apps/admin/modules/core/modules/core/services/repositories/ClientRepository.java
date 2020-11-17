/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package com.cloudo.wms.modules.core.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudo.wms.modules.core.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
