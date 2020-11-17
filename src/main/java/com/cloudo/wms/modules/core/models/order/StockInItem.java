package com.cloudo.wms.modules.core.models.order;

import com.cloudo.wms.lib._BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.tomcat.util.modeler.BaseModelMBean;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "wms_stock_in_item")
public class StockInItem extends _BaseEntity {

    private int stockInId;
    private String supplier;
    private String sku;
    private String barcode;
    private BigDecimal costPrice;
    private String attribute;
    private String brand;
    private String category;
    private String color;
    private String gender;
    private String comment;
    private Integer purchaseQuantity;
    private Integer tallyQuantity;
    private Integer tallyDifferenceQuantity;
    private Integer stockInQuantity;
}
