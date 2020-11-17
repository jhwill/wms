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
@Table(name = "wms_product")
@EntityListeners(AuditingEntityListener.class)
public class Product extends _BaseEntity {

    private String supplier;
    private String name;
    private String sku;
    private String barcode;
    private BigDecimal costPrice;
    private String attribute;
    private String brand;
    private String category;
    private String color;
    private String gender;
    private String comment;
    private int createUserId;
}
