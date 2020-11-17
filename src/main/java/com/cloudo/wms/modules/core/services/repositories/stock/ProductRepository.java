package com.cloudo.wms.modules.core.services.repositories.stock;

import com.cloudo.wms.modules.core.models.stock.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM wms_product  " +
                    "WHERE name LIKE %:keywords% OR barcode LIKE %:keywords%  OR sku LIKE %:keywords%  OR supplier LIKE %:keywords% " +
                    "  OR brand LIKE %:keywords% OR category LIKE %:keywords% OR color LIKE %:keywords%  OR gender LIKE %:keywords% " +
                    "ORDER BY ?#{#pageable}",
            countQuery = "SELECT count(*) FROM wms_product " +
                    "WHERE name LIKE %:keywords% OR barcode LIKE %:keywords%  OR sku LIKE %:keywords%  OR supplier LIKE %:keywords% " +
                    " OR brand LIKE %:keywords% OR category LIKE %:keywords% OR color LIKE %:keywords%  OR gender LIKE %:keywords% "
    )
    Page<Product> search(@Param(value = "keywords") String keywords, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wms_product WHERE id = :id", nativeQuery = true)
    void deleteProductById(@Param(value = "id") long id);


    @Query(value = "SELECT * FROM wms_product WHERE barcode = :barcode", nativeQuery = true)
    Product findByBarcode(@Param(value = "barcode") String barcode);

    @Query(value = "SELECT * FROM wms_product WHERE name = :name", nativeQuery = true)
    Product findByName(@Param(value = "name") String name);
}
