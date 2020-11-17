package com.cloudo.wms.modules.core.services.stock;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.core.models.stock.Product;
import com.cloudo.wms.modules.core.models.stock.Stock;
import com.cloudo.wms.modules.core.services.repositories.stock.ProductRepository;
import com.cloudo.wms.modules.core.services.repositories.stock.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class StockService extends _BaseService<Stock>{

    @Autowired
    private StockRepository stockRepository;

    public Page<Stock> getStock(Pageable pageable) {
        return this.stockRepository.findAll(pageable);
    }

    public Page<Stock> searchStock(String keywords, Pageable pageable) {
        if (keywords != null && !keywords.isEmpty()) {
            return this.stockRepository.search(keywords, pageable);
        } else {
            return this.getStock(pageable);
        }
    }
}
