package com.cloudo.wms.modules.core.services.order;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.core.models.order.Order;
import com.cloudo.wms.modules.core.models.order.OrderItem;
import com.cloudo.wms.modules.core.models.stock.Product;
import com.cloudo.wms.modules.core.services.repositories.order.OrderItemRepository;
import com.cloudo.wms.modules.core.services.repositories.order.OrderRepository;
import com.cloudo.wms.modules.core.services.repositories.stock.ProductRepository;
import com.cloudo.wms.modules.core.services.repositories.stock.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService extends _BaseService<Order>{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private StockRepository stockRepository;

    public Page<Order> getOrder(Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }

    public Page<Order> searchOrder(String keywords, Pageable pageable) {
        if (keywords != null && !keywords.isEmpty()) {
            return this.orderRepository.search(keywords, pageable);
        } else {
            return this.getOrder(pageable);
        }
    }

    public List<OrderItem> getOrderItemByOrderNumber(String orderNumber) {
        return orderItemRepository.findByOrderNumber(orderNumber);
    }

    public void markAsOutOfStock(List<Long> ids) {
        ids.forEach(e->{
            OrderItem orderItem = orderItemRepository.findOne(e);
            if (orderItem != null){
                orderItem.setStatus("2");
                orderItemRepository.save(orderItem);

                stockRepository.updateStockBySku(0,orderItem.getSku());
            }else {
                return;
            }
        });
    }

    public void printOrderItem(List<Long> ids) {

    }
}
