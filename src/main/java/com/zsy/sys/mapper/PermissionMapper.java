package com.zsy.sys.mapper;

import com.zsy.sys.domain.Permission;


import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Delete;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zsy
 * @since 2019-08-10
 */
public interface PermissionMapper extends BaseMapper<Permission> {
	
	/**
	 * 根据菜单id或权限id删除sys_role_permission表的数据
	 * @param id
	 */
	@Delete("delete from sys_role_permission where pid=#{value}")
	void deleteRolePermissionPid(Serializable id);

	/**
	 * 根据角色id查询权限和菜单id的集合
	 * @param roleIds
	 * @return
	 */
	List<Integer> queryPermissionIdsByRoleIds(List<Integer> roleIds);

}
