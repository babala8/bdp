//package com.zjft.microservice.treasurybrain.tauro.web;
//
//import com.zjft.microservice.treasurybrain.common.dto.DTO;
//import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
//import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
//import com.zjft.microservice.treasurybrain.tauro.service.SignService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * @author 常 健
// * @since 2019/08/09
// */
//@Slf4j
//@RestController
//public class SignResourceImpl implements SignResource {
//
//	@Resource
//	private SignService signService;
//
////	@Override
////	public DTO sign(TaskTransferDTO taskTransferDTO) {
////		try {
////			return signService.sign(taskTransferDTO);
////		} catch (Exception e) {
////			log.error("虚拟签收异常", e);
////			return new DTO(RetCodeEnum.EXCEPTION.getCode(),e.getMessage());
////		}
////	}
//}
