package com.cloudo.wms.modules.core.models.stock;

import com.cloudo.wms.lib._BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "wms_product_box")
@EntityListeners(AuditingEntityListener.class)
public class Stock extends _BaseEntity {

    private String sku;
    private long warehouseBoxId;
    private String barcode;
    private long quantity;

}
