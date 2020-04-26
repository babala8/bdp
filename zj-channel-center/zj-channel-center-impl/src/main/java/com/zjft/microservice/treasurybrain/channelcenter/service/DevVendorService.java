package com.zjft.microservice.treasurybrain.channelcenter.service;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevVendorDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

/**
 * @author 杨光
 * @since 2019-04-02
 */
public interface DevVendorService {


	PageDTO<DevVendorDTO> queryDevVendorList(String name);


	DTO addDevVendor(DevVendorDTO dto);


	DTO modDevVendor(DevVendorDTO dto);


	DTO delDevVendorByNo(String no);

}
