package com.zjft.microservice.treasurybrain.usercenter.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.domain.PostUserInfoDO;
import com.zjft.microservice.treasurybrain.usercenter.domain.SysPostDetailDO;
import com.zjft.microservice.treasurybrain.usercenter.dto.PostUserInfoDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPostDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPostDetailDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPostLimitDTO;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.SysPostConverter;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.SysPostLimitConverter;
import com.zjft.microservice.treasurybrain.usercenter.po.SysPostLimitPO;
import com.zjft.microservice.treasurybrain.usercenter.po.SysPostPO;
import com.zjft.microservice.treasurybrain.usercenter.repository.SysPostLimitMapper;
import com.zjft.microservice.treasurybrain.usercenter.repository.SysPostMapper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 常 健
 * @since 2020/1/9
 */
@Slf4j
@ZjComponentResource(group = "sysPost")
public class SysPostComponent {

	@Resource
	SysPostMapper sysPostMapper;

	@Resource
	private SysPostLimitMapper sysPostLimitMapper;


	/**
	 * 岗位删除验证
	 */
	@ZjComponentMapping("deletePostCheck")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String deletePostCheck(String postNo, DTO returnDTO, String str) {
		int userNum = sysPostMapper.qryUserNumByPostNo(postNo);
		if (userNum > 0) {
			returnDTO.setRetMsg("有人员仍在该岗位，无法删除");
			returnDTO.setRetCode("FF");
			return "fail";
		}
		return "ok";
	}


	/**
	 * 条件查询人员参数校验
	 */
	@ZjComponentMapping("qryUserCheck")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String qryUserCheck(HashMap<String, Object> map1, ListDTO<PostUserInfoDTO> returnDTO, String str) {
		String postNo = StringUtil.parseString(map1.get("postNo"));
		String orgNo = StringUtil.parseString(map1.get("orgNo"));
		if (StringUtil.isNullorEmpty(postNo) || StringUtil.isNullorEmpty(orgNo)) {
			returnDTO.setResult(RetCodeEnum.PARAM_LACK);
			return "fail";
		}
		return "ok";
	}


	/**
	 * 增加岗位
	 */
	@ZjComponentMapping("addPost")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String addPost(SysPostDTO sysPostDTO, DTO returnDTO, String str) {
		String maxNo = sysPostMapper.maxNo();
		String newPostNo;
		if (StringUtil.isNullorEmpty(maxNo)) {
			newPostNo = "000001";
		} else {
			Integer tmpInt = Integer.parseInt(maxNo) + 1;
			newPostNo = String.format("%06d", tmpInt);
		}
		sysPostDTO.setPostNo(newPostNo);
		SysPostPO sysPostPO = SysPostConverter.INSTANCE.dto2po(sysPostDTO);
		int x = sysPostMapper.add(sysPostPO);
		if (x != 1) {
			log.error("增加岗位信息失败！");
			returnDTO.setRetCode("FF");
			returnDTO.setRetMsg("增加岗位信息失败！");
			return "fail";
		}
		List<SysPostLimitDTO> dtoList = sysPostDTO.getPostLimitList();
		List<SysPostLimitPO> poList = SysPostLimitConverter.INSTANCE.dto2domain(dtoList);
		if (sysPostDTO.getPostLimitList().size() != 0) {
			sysPostLimitMapper.insertPostLimit(newPostNo, poList);
		}
		returnDTO.setRetMsg("增加岗位信成功！");
		returnDTO.setRetCode("00");
		return "ok";
	}


	/**
	 * 修改岗位
	 */
	@ZjComponentMapping("modPost")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String modPost(SysPostDTO sysPostDTO, DTO returnDTO, String str) {
		SysPostPO sysPostPO = SysPostConverter.INSTANCE.dto2po(sysPostDTO);
		List<SysPostLimitDTO> dtoList = sysPostDTO.getPostLimitList();
		List<SysPostLimitPO> poList = SysPostLimitConverter.INSTANCE.dto2domain(dtoList);
		sysPostLimitMapper.delete(sysPostDTO.getPostNo());
		if (poList.size() != 0) {
			sysPostLimitMapper.insertPostLimit(sysPostDTO.getPostNo(), poList);
		}
		int y = sysPostMapper.mod(sysPostPO);
		if (y != 1) {
			log.error("修改岗位信息失败！");
			returnDTO.setRetCode("FF");
			returnDTO.setRetMsg("修改岗位信息失败！");
			throw new RuntimeException("修改岗位信息失败");
		}
		returnDTO.setRetMsg("修改岗位信息成功！");
		returnDTO.setRetCode("00");
		return "ok";
	}

	/**
	 * 删除岗位
	 */
	@ZjComponentMapping("deletaPost")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String deletePost(String postNo, DTO returnDTO, String str) {
		int rlt = sysPostMapper.del(postNo);
		sysPostLimitMapper.delete(postNo);
		if (rlt > 0) {
			returnDTO.setRetCode("00");
			returnDTO.setRetMsg("删除成功！");
			return "ok";
		} else {
			log.error("删除岗位信息失败！");
			returnDTO.setRetMsg("删除失败！");
			returnDTO.setRetCode("FF");
			return "fail";
		}
	}

	/**
	 * 分页查询岗位信息
	 */
	@ZjComponentMapping("qryPostByPage")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String qryPostByPage(HashMap<String, Object> requestDTO, PageDTO<SysPostDTO> returnDTO, String str) {
		int pageSize = PageUtil.transParam2Page(requestDTO, returnDTO);
		int totalRow = sysPostMapper.qryTotalRow(requestDTO);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<SysPostPO> doList = sysPostMapper.qryByPage(requestDTO);
		List<SysPostDTO> dtoList = SysPostConverter.INSTANCE.po2dto(doList);

		returnDTO.setRetList(dtoList);
		returnDTO.setTotalPage(totalPage);
		returnDTO.setTotalRow(totalRow);
		returnDTO.setRetCode("00");
		returnDTO.setRetMsg("分页查询岗位信息成功！");
		return "ok";
	}


	/**
	 * 查询岗位详情验证
	 */
	@ZjComponentMapping("qryPostDetailCheck")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String qryPostDetailCheck(String postNo, ListDTO<SysPostDetailDTO> returnDTO, String str) {
		int x = sysPostMapper.qryCount(postNo);
		if (x < 1) {
			returnDTO.setRetMsg("此岗位不存在！");
			returnDTO.setRetCode("FF");
			return "fail";
		}
		return "ok";
	}


	/**
	 * 查询岗位详情
	 */
	@ZjComponentMapping("qryPostDetail")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String qryPostDetail(String postNo, ListDTO<SysPostDetailDTO> returnDTO, String string) {
		List<SysPostLimitPO> postLimitList = sysPostLimitMapper.qryByPostNo(postNo);
		SysPostPO sysPostPO = sysPostMapper.select(postNo);
		SysPostDetailDO sysPostDetailDO = new SysPostDetailDO();
		sysPostDetailDO.setPostLimitList(postLimitList);
		sysPostDetailDO.setPostNo(postNo);
		sysPostDetailDO.setPostName(sysPostPO.getPostName());
		sysPostDetailDO.setPostType(sysPostPO.getPostType());
		sysPostDetailDO.setNote(sysPostPO.getNote());
		List<SysPostDetailDO> doList = new ArrayList<>();
		doList.add(sysPostDetailDO);

		List<SysPostDetailDTO> dtoList = SysPostConverter.INSTANCE.domain2dto(doList);
		returnDTO.setRetList(dtoList);
		returnDTO.setRetMsg("成功");
		returnDTO.setRetCode("00");
		return "ok";
	}

	/**
	 * 查询所有岗位信息
	 */
	@ZjComponentMapping("qryAllPostInfo")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String qryAllPostInfo(String string, ListDTO<SysPostDTO> returnDTO, String str) {
		List<SysPostPO> sysPostPOList = sysPostMapper.qryAll();
		List<SysPostDTO> sysPostDTOList = SysPostConverter.INSTANCE.po2dto(sysPostPOList);
		returnDTO.setRetMsg("查询成功！");
		returnDTO.setRetCode("00");
		returnDTO.setRetList(sysPostDTOList);
		return "ok";
	}

	/**
	 * 通过岗位编号机构号查询人员信息
	 */
	@ZjComponentMapping("qryUserByPostNoAndOrgNo")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String qryUserByPostNoAndOrgNo(HashMap<String, Object> map, ListDTO<PostUserInfoDTO> returnDTO, String str) {
		List<PostUserInfoDO> doList = sysPostLimitMapper.qryUserByPostNo(StringUtil.parseString(map.get("postNo")), StringUtil.parseString(map.get("orgNo")));
		List<PostUserInfoDTO> dtoList = SysPostConverter.INSTANCE.doList2dtoList(doList);
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		returnDTO.setRetList(dtoList);
		return "ok";
	}

}
