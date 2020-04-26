package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;

@Data
public class DevTypeTable {
    private String no;

    private String name;

    private String devVendor;

    private String devCatalog;

    private String spec;

    private String weight;

    private String watt;

    private String cashType;
    
    private DevCatalogTable devCatalogTable;

    private DevVendorTable devVendorTable;

}
