package com.zjft.microservice.treasurybrain.usercenter.aop;

import com.zjft.microservice.authadmin.web.CacheResource;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.Duration;
import java.util.Set;

/**
 * 用户、角色 缓存切面类
 * - 用户信息缓存 （用户信息、角色列表）
 * - (停用) 用户详情缓存 （用户信息、角色列表、菜单列表）
 * - 角色权限缓存
 *
 * @author 杨光
 * @since 2019-04-23
 */
@Aspect
@Component
@Slf4j
public class UserRoleCacheAop {

	/**
	 * brain:user:info
	 */
	private final String USER_INFO_KEY = "brain:user:info:";

	private final String USER_ALL_KEY = "brain:user:*";

	@Resource
	private CacheResource cacheResource;

	@Resource
	private RedisTemplate<String, Serializable> commonRedisTemplate;


	@Pointcut("execution(* com.zjft.microservice.treasurybrain.usercenter.service.impl.SysUserServiceImpl.update*(..))")
	public void updateUserPointcut() {
	}

	@Pointcut("execution(* com.zjft.microservice.treasurybrain.usercenter.service.impl.SysUserServiceImpl.delete*(..))")
	public void deleteUserPointcut() {
	}

	@Pointcut("execution(* com.zjft.microservice.treasurybrain.usercenter.service.impl.SysUserServiceImpl.getCurrentUserInfo(..))")
	public void getUserInfoPointcut() {
	}

	@Pointcut("execution(* com.zjft.microservice.treasurybrain.usercenter.service.impl.SysRoleServiceImpl.add*(..))")
	public void addRolePointcut() {
	}

	@Pointcut("execution(* com.zjft.microservice.treasurybrain.usercenter.service.impl.SysRoleServiceImpl.update*(..))")
	public void updateRolePointcut() {
	}

	@Pointcut("execution(* com.zjft.microservice.treasurybrain.usercenter.service.impl.SysRoleServiceImpl.delete*(..))")
	public void deleteRolePointcut() {
	}


	//----------------------------------------------------
	//                  用户信息缓存处理
	//----------------------------------------------------

	@Around("getUserInfoPointcut()")
	public Object aroundUserInfo(ProceedingJoinPoint joinPoint) {
		log.debug("@Around() - getUserInfoPointcut() - aroundUserInfo()");
		UserDTO dto = null;
		try {
			// 取出缓存
			String username = ServletRequestUtil.getUsername();
			dto = (UserDTO) commonRedisTemplate.opsForValue().get(USER_INFO_KEY + username);
			log.info("[从请求头获取的用户名]: {}", username);
			if (dto != null) {
				return dto;
			}

			// 无缓存则查询数据库，并缓存到redis中
			dto = (UserDTO) joinPoint.proceed();
			commonRedisTemplate.opsForValue().set(USER_INFO_KEY + username, dto, Duration.ofHours(12));
		} catch (Throwable throwable) {
			log.error("[user aop redis exception]: ", throwable);
		}
		return dto;
	}


	//----------------------------------------------------
	//                  用户缓存清除
	//----------------------------------------------------

	@AfterReturning(" updateUserPointcut()")
	public void afterUserUpdate(JoinPoint joinPoint) {
		log.debug("@AfterReturning() - updateUserPointcut() - afterUserUpdate()");
		UserDTO dto = (UserDTO) joinPoint.getArgs()[0];
		removeUser(dto.getUsername());
	}


	@AfterReturning("deleteUserPointcut()")
	public void afterUserDelete(JoinPoint joinPoint) {
		log.debug("@AfterReturning() - deleteUserPointcut() - afterUserDelete()");
		String username = joinPoint.getArgs()[0].toString();
		removeUser(username);
	}

	private void removeUser(String username) {
		try {
			commonRedisTemplate.delete(USER_INFO_KEY.concat(username));
			/*commonRedisTemplate.delete(USER_DETAIL_KEY.concat(username));*/
		} catch (Exception e) {
			log.error("[removeUser redis key exception]: ", e);
		}
	}

	@After("updateRolePointcut() || deleteRolePointcut()")
	public void afterRoleChange() {
		log.debug("@After() - deletePointcut() || deleteRolePointcut() - afterRoleChange()");
		try {
			Set<String> keys = commonRedisTemplate.keys(USER_ALL_KEY);
			commonRedisTemplate.delete(keys);
		} catch (Exception e) {
			log.error("[removeUser redis keys exception]: ", e);
		}
	}



	//----------------------------------------------------
	//                  资源权限映射缓存刷新
	//----------------------------------------------------

	@After("addRolePointcut() || updateRolePointcut() || deleteRolePointcut()")
	public void afterAnyRoleChange() {
		log.debug("@After() - addRolePointcut() || updateRolePointcut() || deleteRolePointcut() - afterAnyRoleChange()");

		try {
			cacheResource.refreshCache();
		} catch (Exception e) {
			log.error("[cacheResource.refreshCache() exception]: ", e);
		}
	}
}
