package com.zjft.microservice.treasurybrain.storage.po;

import lombok.Data;

@Data
public class StorageTaskShelfUserPO {
    private String id;

    private String taskNo;

    private String shelfNo;

    private Integer opType;

    private String opTime;


}
