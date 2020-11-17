package com.cloudo.wms.modules.core.services.warehouse;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.core.models.warehouse.Shelf;
import com.cloudo.wms.modules.core.models.warehouse.Warehouse;
import com.cloudo.wms.modules.core.services.repositories.warehouse.ShelfRepository;
import com.cloudo.wms.modules.core.services.repositories.warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ShelfService extends _BaseService<Shelf>{

    @Autowired
    private ShelfRepository shelfRepository;

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
}
