package com.cloudo.wms.modules.core.services.repositories.order;

import com.cloudo.wms.modules.core.models.order.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StockInRepository extends JpaRepository<OrderItem, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM wms_order_item " +
                    "WHERE order_id LIKE %:keywords% OR sku LIKE %:keywords% "+
                    "ORDER BY ?#{#pageable}",
            countQuery = "SELECT count(*) FROM wms_order_item " +
                    "WHERE order_id LIKE %:keywords% OR sku LIKE %:keywords% "
    )
    Page<OrderItem> search(@Param(value = "keywords") String keywords, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wms_order WHERE id = :id", nativeQuery = true)
    void deleteOrderById(@Param(value = "id") long id);

    @Query(value = "SELECT i.* FROM wms_order o LEFT JOIN wms_order_item i ON i.order_id = o.id " +
            "WHERE o.order_number = :orderNumber", nativeQuery = true)
    List<OrderItem> findByOrderNumber(@Param(value = "orderNumber") String orderNumber);
}
