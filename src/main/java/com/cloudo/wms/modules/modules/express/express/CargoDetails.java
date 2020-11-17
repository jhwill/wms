package com.cloudo.wms.modules.modules.express.express;

import lombok.Data;

@Data
public class CargoDetails {

    private double count;
    private String unit;
    private double weight;
    private double amount;
    private String currency;
    private String name;
    private String sourceArea;
}