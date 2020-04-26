package com.zjft.microservice.treasurybrain.param.domain;

import java.math.BigDecimal;

public class SpdateCoeff extends SpdateCoeffKey {

    private BigDecimal addnotesCoeff;

    public BigDecimal getAddnotesCoeff() {
        return addnotesCoeff;
    }

    public void setAddnotesCoeff(BigDecimal addnotesCoeff) {
        this.addnotesCoeff = addnotesCoeff;
    }

}
