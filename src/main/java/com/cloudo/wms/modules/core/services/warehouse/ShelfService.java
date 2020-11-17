package com.cloudo.wms.modules.core.services.warehouse;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.core.models.warehouse.Area;
import com.cloudo.wms.modules.core.models.warehouse.Shelf;
import com.cloudo.wms.modules.core.services.repositories.warehouse.AreaRepository;
import com.cloudo.wms.modules.core.services.repositories.warehouse.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ShelfService extends _BaseService<Shelf>{

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private AreaRepository areaRepository;

    public Page<Shelf> getWarehouse(Pageable pageable) {
        return this.shelfRepository.findAll(pageable);
    }

    public Page<Shelf> searchShelf(String keywords, Pageable pageable) {
        if (keywords != null && !keywords.isEmpty()) {
            return this.shelfRepository.search(keywords, pageable);
        } else {
            return this.getWarehouse(pageable);
        }
    }

    public Page<Shelf> saveShelf(String shelfReference, String areaName) {
        Area area = areaRepository.findByName(areaName);
        if (area != null){
            Shelf shelf = shelfRepository.findByReference(shelfReference);
            if (shelf == null){
                shelf = new Shelf();
                shelf.setReference(shelfReference);
            }
            shelf.setWarehouseAreaId(area.getId());

            shelfRepository.save(shelf);
        }

        Pageable pageable = new PageRequest(0,10, Sort.Direction.DESC,"createDate");
        return shelfRepository.findAll(pageable);
    }
}
