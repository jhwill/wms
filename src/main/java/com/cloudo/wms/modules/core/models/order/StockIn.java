package com.cloudo.wms.modules.core.models.order;

import com.cloudo.wms.lib._BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "wms_stock_in")
public class StockIn extends _BaseEntity {

    private String stockInNumber;
    private String type;
    private String status;
}
