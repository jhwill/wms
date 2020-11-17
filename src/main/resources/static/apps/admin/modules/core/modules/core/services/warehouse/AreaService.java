package com.cloudo.wms.modules.core.services.warehouse;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.core.models.warehouse.Area;
import com.cloudo.wms.modules.core.models.warehouse.Warehouse;
import com.cloudo.wms.modules.core.services.repositories.warehouse.AreaRepository;
import com.cloudo.wms.modules.core.services.repositories.warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AreaService extends _BaseService<Area>{

    @Autowired
    private AreaRepository areaRepository;

    public Page<Area> getWarehouse(Pageable pageable) {
        return this.areaRepository.findAll(pageable);
    }

    public Page<Area> searchArea(String keywords, Pageable pageable) {
        if (keywords != null && !keywords.isEmpty()) {
            return this.areaRepository.search(keywords, pageable);
        } else {
            return this.getWarehouse(pageable);
        }
    }
}
