package com.cloudo.wms.modules.core.models.order;

import com.cloudo.wms.lib._BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "wms_order")
@EntityListeners(AuditingEntityListener.class)
public class Order extends _BaseEntity {
    private String orderNumber;
    private String status;
    private String name;
    private String phone;
    private String address;
    private String postCode;
    private String expressCode;
    private String expressNumber;
}
