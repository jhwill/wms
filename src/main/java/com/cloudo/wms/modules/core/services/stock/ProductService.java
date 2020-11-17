package com.cloudo.wms.modules.core.services.stock;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.core.models.stock.Product;
import com.cloudo.wms.modules.core.models.warehouse.Warehouse;
import com.cloudo.wms.modules.core.services.repositories.stock.ProductRepository;
import com.cloudo.wms.modules.core.services.repositories.warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductService extends _BaseService<Product>{

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProduct(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    public Page<Product> searchProduct(String keywords, Pageable pageable) {
        if (keywords != null && !keywords.isEmpty()) {
            return this.productRepository.search(keywords, pageable);
        } else {
            return this.getProduct(pageable);
        }
    }
}
