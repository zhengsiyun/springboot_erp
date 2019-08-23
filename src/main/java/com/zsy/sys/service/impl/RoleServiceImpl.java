package com.zsy.sys.service.impl;

import com.zsy.sys.domain.Permission;
import com.zsy.sys.domain.Role;
import com.zsy.sys.mapper.RoleMapper;
import com.zsy.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-08-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
		
	@Override
	public boolean removeById(Serializable id) {
		this.getBaseMapper().deleteRolePermissionByRid(id);
		this.getBaseMapper().deleteRoleUserByRoleId(id);
		boolean removeById = super.removeById(id);
		//删除sys_role_permission里的数据
		return removeById;
	}

	@Override
	public List<Integer> queryAllPermissionByRid(Integer id) {
		
		return this.getBaseMapper().queryAllPermissionByRid(id);
	}

	@Override
	public void saveRolePermission(Integer id, List<Integer> ids) {
		//1.根据id删除当前id下的所有菜单
		this.getBaseMapper().deleteRolePermissionByRid(id);
		//2.保存角色和权限之间的关系
		if (null!=ids&&ids.size()>0) {
			for (Integer pid : ids) {
				this.getBaseMapper().insertRolePermission(id,pid);
				
			}
		}
		
	}

	@Override
	public List<Integer> queryRoleUserByUserId(Integer id) {
		
		return this.getBaseMapper().queryRoleUserByUserId(id);
	}
	
	@Override
	public List<String> queryRoleNameUserByUserId(Integer id) {
		//根据用户id查询角色集合
		List<Integer> roleids = this.getBaseMapper().queryRoleUserByUserId(id);
		if (roleids.size()==0) {
			return new ArrayList<String>();
		}
		//根据角色ids查询角色名称
		List<String> roleNames = this.getBaseMapper().queryRoleNameByIds(roleids);
		return roleNames;
	}
}
