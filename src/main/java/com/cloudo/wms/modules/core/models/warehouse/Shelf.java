package com.cloudo.wms.modules.core.models.warehouse;

import com.cloudo.wms.lib._BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "wms_warehouse_shelf")
@EntityListeners(AuditingEntityListener.class)
public class Shelf extends _BaseEntity {

    private long warehouseAreaId;
    private String reference;
    private int status;
    private int createUserId;

}
