package com.cloudo.wms.modules.modules.express.express;
import lombok.Data;

import java.util.List;

@Data
public class SFOrderBean {
    private List<CargoDetails> cargoDetails;
    private List<ContactInfoList> contactInfos;
    private String language = "zh-CN";
    private String orderId;
}