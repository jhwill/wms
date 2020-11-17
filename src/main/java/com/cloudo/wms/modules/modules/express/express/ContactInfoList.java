package com.cloudo.wms.modules.modules.express.express;

import lombok.Data;

@Data
public class ContactInfoList {

    private String address;
    private String contact;
    private int contactType;
    private String country;
    private String postCode;
    private String tel;
}