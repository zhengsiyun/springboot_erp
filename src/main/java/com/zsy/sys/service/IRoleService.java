package com.zsy.sys.service;

import com.zsy.sys.domain.Permission;
import com.zsy.sys.domain.Role;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zsy
 * @since 2019-08-12
 */
public interface IRoleService extends IService<Role> {

	List<Integer> queryAllPermissionByRid(Integer id);

	void saveRolePermission(Integer id, List<Integer> ids);

	List<Integer> queryRoleUserByUserId(Integer id);
	
	List<String> queryRoleNameUserByUserId(Integer id);
}
