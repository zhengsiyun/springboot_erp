package com.zsy.sys.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.sys.constast.SysConstast;
import com.zsy.sys.domain.Permission;
import com.zsy.sys.service.IPermissionService;
import com.zsy.sys.utils.DataGridView;
import com.zsy.sys.utils.ResultObj;
import com.zsy.sys.vo.PermissionVo;

@RestController
@RequestMapping("/permission")
public class PermissionController {
	@Autowired
	private IPermissionService permissionService;
	

	/**
	 * 记载权限管理右边的数据
	 */
	@RequestMapping("loadAllPermission")
	public DataGridView loadAllPermission(PermissionVo permissionVo) {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("type", SysConstast.PERMISSION_TYPE_PERMISSION);
		if (StringUtils.isNotBlank(permissionVo.getTitle())) {
			queryWrapper.like("title", permissionVo.getTitle());
		}
		if (StringUtils.isNotBlank(permissionVo.getPercode())) {
			queryWrapper.like("percode", permissionVo.getPercode());
		}
		if (permissionVo.getAvailable()!=null) {
			queryWrapper.eq("available", permissionVo.getAvailable());
		}
		if (permissionVo.getId()!=null) {
			queryWrapper.eq("pid", permissionVo.getId());
		}
		IPage<Permission> page = new Page<Permission>(permissionVo.getPage(),permissionVo.getLimit());
		permissionService.page(page,queryWrapper);
		return new DataGridView(page.getTotal(),page.getRecords());
		
	}
	
	/**
	 * 查询权限的最大排序码
	 */
	@RequestMapping("loadPermissionMaxOrderNumber")
	public Map<String, Object> loadPermissionMaxOrderNumber(){
		Map<String, Object> map = new HashMap<>();
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("ordernum");
		queryWrapper.orderByDesc("ordernum").last("LIMIT 1");
		Permission permission = this.permissionService.getOne(queryWrapper);
		map.put("value", permission.getOrdernum()+1);
		return map;
	}
	
	/**
	 * 添加权限
	 */
	@RequestMapping("addPermission")
	public ResultObj addPermission(PermissionVo permissionVo) {
		try {
			permissionVo.setType(SysConstast.PERMISSION_TYPE_PERMISSION);
			this.permissionService.save(permissionVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 修改权限
	 */
	@RequestMapping("updatePermission")
	public ResultObj updatePermission(PermissionVo permissionVo) {
		try {
			permissionService.updateById(permissionVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	
	
	/**
	 * 删除权限
	 */
	@RequestMapping("deletePermission")
	public ResultObj deletePermission(Integer id) {
		try {
			permissionService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
}
