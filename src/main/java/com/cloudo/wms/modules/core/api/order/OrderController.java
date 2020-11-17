package com.cloudo.wms.modules.core.api.order;

import com.cloudo.wms.exceptions.ResourceIntegrityException;
import com.cloudo.wms.lib._BaseApi;
import com.cloudo.wms.modules.core.models.order.Order;
import com.cloudo.wms.modules.core.models.order.OrderItem;
import com.cloudo.wms.modules.core.models.stock.Product;
import com.cloudo.wms.modules.core.services.order.OrderService;
import com.cloudo.wms.modules.core.services.stock.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "Order API")
@RestController
@RequestMapping({"/api/core/order", "/api/v1/core/order"})
public class OrderController extends _BaseApi<Order> {

    @Autowired
    private OrderService orderService;


    @Override
    public Page<Order> get(@PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return super.get(pageable);
    }

    @ApiOperation(value = "Search by keywords")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Page<Order> search(
            @RequestParam(name = "keywords", required = false) String keywords,
            @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return this.orderService.searchOrder(keywords, pageable);
    }

    @Override
    public void delete(@PathVariable(name = "id") long id) throws ResourceIntegrityException {
        try {
            super.delete(id);
        } catch (Exception ex) {
            throw new ResourceIntegrityException("admin.modules.core.order.errForDelete", ex);
        }
    }

    @ApiOperation(value = "Search Order Item by Order Number")
    @RequestMapping(value = "/getOrderItemByOrderNumber", method = RequestMethod.GET)
    public List<OrderItem> getOrderItemByOrderNumber(@RequestParam(name = "orderNumber", required = false) String orderNumber) {
        return this.orderService.getOrderItemByOrderNumber(orderNumber);
    }

    @ApiOperation(value = "Print Order Item")
    @RequestMapping(value = "/printOrderItem", method = RequestMethod.POST)
    public void printOrderItem(@RequestBody List<Long> ids) throws ResourceIntegrityException {
        try {
            this.orderService.printOrderItem(ids);
        } catch (Exception ex) {
            throw new ResourceIntegrityException("admin.modules.core.order.errMarkAsOutOfStock", ex);
        }
    }

    @ApiOperation(value = "Mark Sku Out Of Stock")
    @RequestMapping(value = "/markAsOutOfStock", method = RequestMethod.POST)
    public void markAsOutOfStock(@RequestBody List<Long> ids, HttpServletRequest request) throws ResourceIntegrityException {

        try {
            this.orderService.markAsOutOfStock(ids);
        } catch (Exception ex) {
            throw new ResourceIntegrityException("admin.modules.core.order.errMarkAsOutOfStock", ex);
        }
    }
}
