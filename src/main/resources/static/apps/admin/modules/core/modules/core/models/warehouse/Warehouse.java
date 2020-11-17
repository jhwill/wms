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
@Table(name = "wms_warehouse")
@EntityListeners(AuditingEntityListener.class)
public class Warehouse extends _BaseEntity {

    private String name;
    private String description;
    private int status;
    private int createUserId;

}
