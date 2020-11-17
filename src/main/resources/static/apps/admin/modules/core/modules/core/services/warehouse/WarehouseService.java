package com.cloudo.wms.modules.core.services.warehouse;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.core.models.warehouse.Warehouse;
import com.cloudo.wms.modules.core.services.repositories.warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class WarehouseService extends _BaseService<Warehouse>{

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Page<Warehouse> getWarehouse(Pageable pageable) {
        return this.warehouseRepository.findAll(pageable);
    }

    public Page<Warehouse> searchWarehouse(String keywords, Pageable pageable) {
        if (keywords != null && !keywords.isEmpty()) {
            return this.warehouseRepository.search(keywords, pageable);
        } else {
            return this.getWarehouse(pageable);
        }
    }
}
