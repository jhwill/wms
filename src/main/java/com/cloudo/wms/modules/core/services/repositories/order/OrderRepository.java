package com.cloudo.wms.modules.core.services.repositories.order;

import com.cloudo.wms.modules.core.models.order.Order;
import com.cloudo.wms.modules.core.models.stock.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM wms_order " +
                    "WHERE order_number LIKE %:keywords% OR name LIKE %:keywords%  OR phone LIKE %:keywords%  OR exprss_number LIKE %:keywords% " +
                    "ORDER BY ?#{#pageable}",
            countQuery = "SELECT count(*) FROM wms_order " +
                    "WHERE order_number LIKE %:keywords% OR name LIKE %:keywords%  OR phone LIKE %:keywords%  OR exprss_number LIKE %:keywords% "
    )
    Page<Order> search(@Param(value = "keywords") String keywords, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wms_order WHERE id = :id", nativeQuery = true)
    void deleteOrderById(@Param(value = "id") long id);

    @Query(value = "SELECT * FROM wms_order WHERE order_number = :orderNumber", nativeQuery = true)
    Order findByOrderNumber(@Param(value = "orderNumber") String orderNumber);
}
