package com.zsy.sys.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.sys.constast.SysConstast;
import com.zsy.sys.domain.Permission;
import com.zsy.sys.domain.Role;
import com.zsy.sys.service.IPermissionService;
import com.zsy.sys.service.IRoleService;
import com.zsy.sys.utils.DataGridView;
import com.zsy.sys.utils.ResultObj;
import com.zsy.sys.utils.TreeNode;
import com.zsy.sys.vo.RoleVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsy
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private  IPermissionService permissionService;
	
	/**
	 * 全查询 角色
	 * @param roleVo
	 * @return
	 */
	@RequestMapping("loadAllRole")
	public DataGridView loadAllRole(RoleVo roleVo) {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(roleVo.getName())) {
			queryWrapper.like("name", roleVo.getName());
		}
		if (StringUtils.isNotBlank(roleVo.getRemark())) {
			queryWrapper.like("remark", roleVo.getRemark());
		}
		if (roleVo.getAvailable()!=null) {
			queryWrapper.eq("available", roleVo.getAvailable());
		}
		Page<Role> page = new Page<>(roleVo.getPage(),roleVo.getLimit());
		roleService.page(page,queryWrapper);
		return new DataGridView(page.getTotal(), page.getRecords());
	}
	
	/**
	 * 加载分配弹出层dtree里面的数据
	 */
	@RequestMapping("initRolePermissionTreeJson")
	public DataGridView initRolePermissionTreeJson(Integer id) {
		//查询所有可用权限和菜单
		QueryWrapper<Permission> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("available", SysConstast.AVAILABLE_TRUE);
		List<Permission> allPermission = permissionService.list(queryWrapper1);
		
		//根据id查询当前角色下的菜单
		List<Integer> permissionIds = roleService.queryAllPermissionByRid(id);
		List<Permission> currentRolePermissions=null;
		if (permissionIds!=null&&permissionIds.size()>0) {
			QueryWrapper<Permission> queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("available", SysConstast.AVAILABLE_TRUE);
			queryWrapper2.eq("id", permissionIds);
			currentRolePermissions = this.permissionService.list(queryWrapper2);
		}else {
			currentRolePermissions = new ArrayList<Permission>();
		}
		List<TreeNode> nodes = new ArrayList<>();
		for(Permission p1: allPermission) {
			String checkArr = "0";
			for(Permission p2: currentRolePermissions) {
				if (p1.getId()==p2.getId()) {
					checkArr = "1";
						break;
				}
			}
			Boolean spread=p1.getOpen()==SysConstast.SPREAD_TRUE?true:false;
			nodes.add(new TreeNode(p1.getId(), p1.getPid(), p1.getTitle(), p1.getIcon(),p1.getHref(),spread, p1.getTarget()));
		}
		return new DataGridView(nodes);
	}
	
	/**
	 * 保存角色和权限的关系
	 */
	@RequestMapping("saveRolePermission")
	public ResultObj saveRolePermission(RoleVo roleVo) {
		try {
			this.roleService.saveRolePermission(roleVo.getId(),roleVo.getIds());
			return ResultObj.DISPATCH_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DISPATCH_ERROR;
		}
	}
	/**
	 * 添加角色
	 * @param roleVo
	 * @return
	 */
	@RequestMapping("addRole")
	public ResultObj addRole(RoleVo roleVo) {
		try {
			roleVo.setCreatetime(new Date());
			roleService.save(roleVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
		
	}
	
	
	/**
	 * 修改角色
	 * @param roleVo
	 * @return
	 */
	@RequestMapping("updateRole")
	public ResultObj updateRole(RoleVo roleVo) {
		try {
			roleService.updateById(roleVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteRole")
	public ResultObj deleteRole(Integer id) {
		try {
			roleService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
}

