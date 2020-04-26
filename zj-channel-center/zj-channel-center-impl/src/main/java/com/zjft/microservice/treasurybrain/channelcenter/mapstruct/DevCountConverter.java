package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CountTaskInfoDO;
import com.zjft.microservice.treasurybrain.channelcenter.domain.DevCountDO;
import com.zjft.microservice.treasurybrain.channelcenter.domain.TagInfoDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CountTaskInfoDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevCountDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.TagDTO;
import com.zjft.microservice.treasurybrain.channelcenter.po.DevCountPO;
import com.zjft.microservice.treasurybrain.channelcenter.po.TagInfoPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 崔耀中
 * @since 2019/10/10
 */
@Mapper
public interface DevCountConverter {

	DevCountConverter INSTANCE = Mappers.getMapper(DevCountConverter.class);

	@Mappings({
			@Mapping(source = "batch", target = "batch"),
			@Mapping(source = "centerName", target = "centerName"),
			@Mapping(source = "doingList", target = "doingList"),
			@Mapping(source = "queueList", target = "queueList"),
			@Mapping(source = "doneList", target = "doneList")
	})

	DevCountDTO domain2dto(DevCountDO devCountDO);

	List<DevCountDTO> domain2dto(List<DevCountDO> devCountList);

	DevCountPO dto2domain(DevCountDTO devCountDTO);

	CountTaskInfoDTO domain2dto(CountTaskInfoDO countTaskInfoDO);

	CountTaskInfoDO dto2domain(CountTaskInfoDTO countTaskInfoDTO);

}
