package com.zjft.microservice.treasurybrain.param.domain;


import java.math.BigDecimal;

public class DevChsEstParam extends DevChsEstParamKey {
    private String estParamName;

    private String estParamDesp;

    private BigDecimal weight;

    private Byte isValid;

    public String getEstParamName() {
        return estParamName;
    }

    public void setEstParamName(String estParamName) {
        this.estParamName = estParamName == null ? null : estParamName.trim();
    }

    public String getEstParamDesp() {
        return estParamDesp;
    }

    public void setEstParamDesp(String estParamDesp) {
        this.estParamDesp = estParamDesp == null ? null : estParamDesp.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }
}
