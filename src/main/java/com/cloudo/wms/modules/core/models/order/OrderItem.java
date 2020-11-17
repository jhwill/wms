package com.cloudo.wms.modules.core.models.order;

import com.cloudo.wms.lib._BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "wms_order_item")
@EntityListeners(AuditingEntityListener.class)
public class OrderItem extends _BaseEntity {

    private Integer orderId;
    private String sku;
    private String status;
    private long quantity;
}
