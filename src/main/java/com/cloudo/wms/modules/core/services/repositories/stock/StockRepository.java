package com.cloudo.wms.modules.core.services.repositories.stock;

import com.cloudo.wms.modules.core.models.stock.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM wms_product_box " +
                    "WHERE barcode LIKE %:keywords% OR sku LIKE %:keywords%  OR warehouse_box_id LIKE %:keywords% " +
                    "ORDER BY ?#{#pageable}",
            countQuery = "SELECT count(*) FROM wms_product_box " +
                    "WHERE barcode LIKE %:keywords% OR sku LIKE %:keywords%  OR warehouse_box_id LIKE %:keywords% "
    )
    Page<Stock> search(@Param(value = "keywords") String keywords, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wms_product_box WHERE id = :id", nativeQuery = true)
    void deleteStockById(@Param(value = "id") long id);

    @Query(value = "SELECT * FROM wms_product_box WHERE barcode = :barcode", nativeQuery = true)
    Stock findByBarcode(@Param(value = "barcode") String barcode);

    @Query(value = "UPDATE wms_product_box SET quantity = :quantity WHERE sku = :sku",nativeQuery = true)
    void updateStockBySku(int quantity, String sku);
}
