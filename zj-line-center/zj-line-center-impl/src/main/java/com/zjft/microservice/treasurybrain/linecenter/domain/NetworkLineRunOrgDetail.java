package com.zjft.microservice.treasurybrain.linecenter.domain;

import lombok.Data;

@Data
public class NetworkLineRunOrgDetail extends NetworkLineRunOrgDetailKey {

    private String networkName;

    private Integer sort;

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
