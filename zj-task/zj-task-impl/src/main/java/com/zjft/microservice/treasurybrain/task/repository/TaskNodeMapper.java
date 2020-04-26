package com.zjft.microservice.treasurybrain.task.repository;


import com.zjft.microservice.treasurybrain.task.dto.TaskNodeDTO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


/**
 * @author 韩通
 * @since 2019/11/01
 */
@Mapper
public interface TaskNodeMapper {

	/**
	 * 插入任务节点信息
	 *
	 * @param taskNodeDTO 任务节点对象
	 * @return int
	 */
	@Insert("<script> " +
			"insert into TASK_NODE_TABLE ( TASK_NODE_NO, TASK_NO, OP_NO, FINISH_TIME, " +
			"BEFORE_NODE, CUR_NODE, DESCRIPTION, OPERATE_TYPE) values " +
			"(#{taskNodeNo}, #{taskNo}, #{opNo}, #{finishTime}, " +
			"#{beforeNode}, #{curNode}, #{description}, #{operateType})" +
			"</script> "
	)
	int insert(TaskNodeDTO taskNodeDTO);

	/**
	 * 批量插入任务节点信息
	 *
	 * @param taskNodeDTOList 任务节点列表
	 * @return int
	 */
	@Insert("<script> " +
			"insert into TASK_NODE_TABLE ( TASK_NODE_NO, TASK_NO, OP_NO, FINISH_TIME, " +
			"CUR_NODE, BEFORE_NODE, DESCRIPTION, OPERATE_TYPE) " +
			"<foreach item='item' index='index' collection='taskNodeDOList' separator='union all' > " +
			"(SELECT #{item.taskNodeNo}, #{item.taskNo}," +
			"#{item.opNo}, #{item.finishTime}, #{item.curNode}, " +
			" #{item.beforeNode}, #{item.description}, #{item.operateType} from dual) " +
			"</foreach>" +
			"</script> "
	)
	int insertBatch(@Param("taskNodeDOList") List<TaskNodeDTO> taskNodeDTOList);

	/**
	 * 根据任务编号查询最大任务节点编号
	 *
	 * @param taskNodeNo 任务编号
	 * @return 最大任务节点编号
	 */
	@Select("select max(TASK_NODE_NO) " +
			"from TASK_NODE_TABLE " +
			"where TASK_NO = #{taskNodeNo,jdbcType=VARCHAR}")
	String getMaxNodeNo(@Param("taskNodeNo") String taskNodeNo);

	/**
	 * 根据任务编号查询该任务的所有节点信息
	 *
	 * @param taskNo 任务编号
	 * @return
	 */
	@Select("select node.TASK_NODE_NO,node.TASK_NO,node.OP_NO,node.FINISH_TIME,node.CUR_NODE,node.BEFORE_NODE,node.DESCRIPTION,node.OPERATE_TYPE,u.NAME,status1.NAME as BEFORE_NODE_NAME,status2.NAME as cur_NODE_NAME,convert.DESCRIPTION as OPERATE_TYPE_NAME,tnv.VALUE  " +
			"from TASK_NODE_TABLE node " +
			"left join SYS_USER u on u.USERNAME = node.OP_NO " +
			"left join TASK_TABLE task on task.TASK_NO = node.TASK_NO " +
			"left join SERVICE_STATUS status1 on status1.SERVICE_NO = TASK_TYPE and status1.STATUS = node.BEFORE_NODE " +
			"left join SERVICE_STATUS status2 on status2.SERVICE_NO = TASK_TYPE and status2.STATUS = node.CUR_NODE " +
			"left join SERVICE_STATUS_CONVERT convert on convert.SERVICE_NO = TASK_TYPE and convert.CUR_STATUS = node.BEFORE_NODE and convert.OPERATE_TYPE = node.OPERATE_TYPE " +
			"left join TASK_NODE_VARIATE tnv on tnv.TASK_NODE_NO = node.TASK_NODE_NO " +
			"where TASK_NO = #{taskNo,jdbcType=VARCHAR} order by node.FINISH_TIME")
	@Results({
			@Result(column = "TASK_NODE_NO", property = "taskNodeNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TASK_NO", property = "taskNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OP_NO", property = "opNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FINISH_TIME", property = "finishTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CUR_NODE", property = "curNode", jdbcType = JdbcType.NUMERIC),
			@Result(column = "BEFORE_NODE", property = "beforeNode", jdbcType = JdbcType.NUMERIC),
			@Result(column = "DESCRIPTION", property = "description", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OPERATE_TYPE", property = "operateType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NAME", property = "opName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BEFORE_NODE_NAME", property = "beforeNodeName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "cur_NODE_NAME", property = "curNodeName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OPERATE_TYPE_NAME", property = "operateTypeName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALUE", property = "operateValue", jdbcType = JdbcType.VARCHAR),
	})
	List<TaskNodeDTO> qryTaskNode(@Param("taskNo") String taskNo);
}
