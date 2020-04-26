package com.zjft.microservice.treasurybrain.storage.repository;


import com.zjft.microservice.treasurybrain.storage.po.StorageShelfTablePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StorageShelfTableMapper {

	/**
	 * 更新笼车状态
	 *
	 * @param storageShelfTablePO
	 * @return 是否成功
	 */
	int updateStatusByNo(StorageShelfTablePO storageShelfTablePO);

	/**
	 * 查询该笼车在基础信息中是否存在
	 *
	 * @param shelfNo 笼车编号
	 * @return 匹配数量
	 */
	int qryShelfNo(String shelfNo);

	/**
	 * 查询笼车是否可用
	 *
	 * @param storageShelfTablePO
	 * @return
	 */
	int qryShelfUnused(StorageShelfTablePO storageShelfTablePO);

	@Select("select STATUS\n" +
			"\t\tfrom SHELF_TABLE\n" +
			"\t\twhere SHELF_NO = #{shelfNo,jdbcType=VARCHAR}")
	int qryShelfStatus(String shelfNo);

	/**
	 * 修改笼车状态
	 * @param shelfNo 笼车编号
	 * @param status 笼车状态
	 * @return
	 */
	int modShelfStatus(@Param("shelfNo") String shelfNo, @Param("status") int status);
}
