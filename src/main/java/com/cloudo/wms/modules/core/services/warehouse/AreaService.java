package com.cloudo.wms.modules.core.services.warehouse;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.core.models.warehouse.Area;
import com.cloudo.wms.modules.core.models.warehouse.Warehouse;
import com.cloudo.wms.modules.core.services.repositories.warehouse.AreaRepository;
import com.cloudo.wms.modules.core.services.repositories.warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AreaService extends _BaseService<Area>{

    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;

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

    public Page<Area> saveArea(String name, String warehouseName,String description) {
        Warehouse warehouse = warehouseRepository.findByName(warehouseName);
        if (warehouse != null){
            Area area = areaRepository.findByName(name);
            if (area ==null){
                area = new Area();
                area.setName(name);
            }
            area.setWarehouseId(warehouse.getId());
            area.setDescription(description);

            areaRepository.save(area);
        }

        Pageable pageable = new PageRequest(0,10, Sort.Direction.DESC,"createDate");
        return areaRepository.findAll(pageable);
    }
}
