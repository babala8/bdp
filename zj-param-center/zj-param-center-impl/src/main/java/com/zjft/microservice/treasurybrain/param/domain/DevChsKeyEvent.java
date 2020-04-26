package com.zjft.microservice.treasurybrain.param.domain;

public class DevChsKeyEvent extends DevChsKeyEventKey {

    private String eventName;

    private String eventDesp;

    private Byte isValid;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public String getEventDesp() {
        return eventDesp;
    }

    public void setEventDesp(String eventDesp) {
        this.eventDesp = eventDesp == null ? null : eventDesp.trim();
    }

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

}
