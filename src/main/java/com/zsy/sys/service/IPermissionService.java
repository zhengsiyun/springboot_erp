package com.zsy.sys.service;

import com.zsy.sys.domain.Permission;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zsy
 * @since 2019-08-10
 */
public interface IPermissionService extends IService<Permission> {

	List<Integer> queryPermissionIdsByRoleIds(List<Integer> roleIds);

	List<String> queryPermissionCodeByUserId(Integer id);


}
