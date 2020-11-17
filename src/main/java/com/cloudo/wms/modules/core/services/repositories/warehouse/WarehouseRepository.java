package com.cloudo.wms.modules.core.services.repositories.warehouse;

import com.cloudo.wms.modules.core.models.warehouse.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM wms_warehouse  " +
                    "WHERE name LIKE %:keywords% OR status LIKE %:keywords% ORDER BY ?#{#pageable}",
            countQuery = "SELECT count(*) FROM wms_warehouse " +
                    "WHERE name LIKE %:keywords% OR status LIKE %:keywords%"
    )
    Page<Warehouse> search(@Param(value = "keywords")String keywords, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wms_warehouse WHERE id = :id", nativeQuery = true)
    void deleteWarehouseById(@Param(value = "id") long id);

    @Query(value = "SELECT * FROM wms_warehouse WHERE name = :name", nativeQuery = true)
    Warehouse findByName(@Param(value = "name") String name);
}
