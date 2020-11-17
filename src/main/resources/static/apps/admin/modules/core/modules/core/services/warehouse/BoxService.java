package com.cloudo.wms.modules.core.services.warehouse;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.core.models.warehouse.Box;
import com.cloudo.wms.modules.core.models.warehouse.Warehouse;
import com.cloudo.wms.modules.core.services.repositories.warehouse.BoxRepository;
import com.cloudo.wms.modules.core.services.repositories.warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BoxService extends _BaseService<Box>{

    @Autowired
    private BoxRepository boxRepository;

    public Page<Box> getWarehouse(Pageable pageable) {
        return this.boxRepository.findAll(pageable);
    }

    public Page<Box> searchBox(String keywords, Pageable pageable) {
        if (keywords != null && !keywords.isEmpty()) {
            return this.boxRepository.search(keywords, pageable);
        } else {
            return this.getWarehouse(pageable);
        }
    }
}
