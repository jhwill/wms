package com.cloudo.wms.modules.core.services.repositories.warehouse;

import com.cloudo.wms.modules.core.models.warehouse.Shelf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM wms_warehouse_shelf  " +
                    "WHERE name LIKE %:keywords% OR status LIKE %:keywords% OR reference LIKE %:keywords% ORDER BY ?#{#pageable}",
            countQuery = "SELECT count(*) FROM wms_warehouse_shelf " +
                    "WHERE name LIKE %:keywords% OR status LIKE %:keywords% OR reference LIKE %:keywords% "
    )
    Page<Shelf> search(@Param(value = "keywords")String keywords, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wms_warehouse WHERE id = :id", nativeQuery = true)
    void deleteWarehouseById(@Param(value = "id") long id);

    Shelf findByReference(String reference);
}
