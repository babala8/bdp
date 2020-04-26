package com.zjft.microservice.treasurybrain.accountscenter.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.accountscenter.domain.BiztxlogDO;
import com.zjft.microservice.treasurybrain.accountscenter.dto.BiztxlogDTO;
import com.zjft.microservice.treasurybrain.accountscenter.mapstruct.TransactionRecordConverter;
import com.zjft.microservice.treasurybrain.accountscenter.repository.BiztxlogMapper;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-07
 */

@Slf4j
@ZjComponentResource(group = "accountcenter")
public class BiztxlogComponent {

	@Resource
	private BiztxlogMapper biztxlogMapper;


	/**
	 * 查询交易记录
	 */
	@ZjComponentMapping("qryBiztxlog")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String qryBiztxlog(HashMap<String, Object> paramMap, PageDTO<BiztxlogDTO> returnDTO, List<String> no) {
		try {
			//PageDTO<BiztxlogDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
			Integer pageSize = PageUtil.transParam2Page(paramMap, returnDTO);

			int totalRow = biztxlogMapper.qryTotalRow(paramMap);
			int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

			List<BiztxlogDO> doList = biztxlogMapper.queryInfoByPage(paramMap);
			List<BiztxlogDTO> dtoList = TransactionRecordConverter.INSTANCE.domain2dto(doList);

			returnDTO.setTotalRow(totalRow);
			returnDTO.setTotalPage(totalPage);
			returnDTO.setPageSize(dtoList.size());
			returnDTO.setRetList(dtoList);
			returnDTO.setResult(RetCodeEnum.SUCCEED);
			return "ok";
		}catch (Exception e){
			return "fail";
		}
	}

}
