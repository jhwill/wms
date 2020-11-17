package com.cloudo.wms.modules.core.services.warehouse;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.core.models.warehouse.Box;
import com.cloudo.wms.modules.core.models.warehouse.Shelf;
import com.cloudo.wms.modules.core.models.warehouse.Warehouse;
import com.cloudo.wms.modules.core.services.repositories.warehouse.BoxRepository;
import com.cloudo.wms.modules.core.services.repositories.warehouse.ShelfRepository;
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
public class BoxService extends _BaseService<Box>{

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private ShelfRepository shelfRepository;

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

    public Page<Box> saveBox(String boxReference, String shelfReference) {
        Shelf shelf = shelfRepository.findByReference(shelfReference);
        if (shelf != null){
            Box box = boxRepository.findByReference(boxReference);
            if (box == null){
                box = new Box();
                box.setReference(boxReference);
            }
            box.setWarehouseShelfId(shelf.getId());

            boxRepository.save(box);
        }

        Pageable pageable = new PageRequest(0,10, Sort.Direction.DESC,"createDate");
        return boxRepository.findAll(pageable);
    }
}
