package com.zsy.sys.service.impl;

import com.zsy.sys.constast.SysConstast;
import com.zsy.sys.domain.Permission;
import com.zsy.sys.mapper.PermissionMapper;
import com.zsy.sys.mapper.RoleMapper;
import com.zsy.sys.service.IPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-08-10
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
		@Autowired
		private RoleMapper roleMapper;
		@Override
		public boolean removeById(Serializable id) {
			boolean delMenu = super.removeById(id);
			this.getBaseMapper().deleteRolePermissionPid(id);
			return delMenu;
		}

		@Override
		public List<Integer> queryPermissionIdsByRoleIds(List<Integer> roleIds) {
			return this.getBaseMapper().queryPermissionIdsByRoleIds(roleIds);
		}

		@Override
		public List<String> queryPermissionCodeByUserId(Integer id) {
			List<String> permissionCodes = new ArrayList<>();
			//根据用户id查询角色id集合
			List<Integer> roleIds = this.roleMapper.queryRoleUserByUserId(id);
			if (roleIds==null || roleIds.size() ==0) {
				return permissionCodes;
			}else {
				//根据角色id查询所有权限id
				List<Integer> permissionIds = this.getBaseMapper().queryPermissionIdsByRoleIds(roleIds);
				if (permissionIds==null || permissionIds.size()==0) {
					return permissionCodes;
				}else {
					QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
					queryWrapper.eq("type", SysConstast.PERMISSION_TYPE_PERMISSION);
					queryWrapper.eq("available", SysConstast.AVAILABLE_TRUE);
					queryWrapper.in("id", permissionIds);
					List<Permission> list = this.getBaseMapper().selectList(queryWrapper);
					for (Permission permission : list) {
						permissionCodes.add(permission.getPercode());
					}
					return permissionCodes;
				}
			}
		}
}
