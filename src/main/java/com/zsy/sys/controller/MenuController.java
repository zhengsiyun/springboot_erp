package com.zsy.sys.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.sys.constast.SysConstast;
import com.zsy.sys.domain.Permission;
import com.zsy.sys.domain.User;
import com.zsy.sys.service.IPermissionService;
import com.zsy.sys.service.IRoleService;
import com.zsy.sys.utils.DataGridView;
import com.zsy.sys.utils.ResultObj;
import com.zsy.sys.utils.TreeNode;
import com.zsy.sys.utils.TreeNodeBuilder;
import com.zsy.sys.utils.WebUtils;
import com.zsy.sys.vo.PermissionVo;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsy
 * @since 2019-08-10
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private IPermissionService permissionService;
	
	@Autowired
	private IRoleService roleService;
	
	/**
	 * 加载首页左边的数据
	 */
	@RequestMapping("loadIndexLeftMenuJson")
	public List<TreeNode> loadIndexLeftMenuJson(PermissionVo permissionVo){
		User user = (User) WebUtils.getHttpSession().getAttribute("user");
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("type", SysConstast.PERMISSION_TYPE_MENU);
		queryWrapper.eq("available", SysConstast.AVAILABLE_TRUE);
		List<Permission> list = null;
		if (user.getType()==SysConstast.USER_TYPE_SUPER) {
			//查询全部
			list = this.permissionService.list(queryWrapper);
		}else {
			//1.根据用户id查询拥有的角色集合
			List<Integer> roleIds = this.roleService.queryRoleUserByUserId(user.getId());
			//2.根据角色id查询所有的菜单
			List<Integer> permissionIds = this.permissionService.queryPermissionIdsByRoleIds(roleIds);
			//3.根据菜单的集合查询
			queryWrapper.in("id", permissionIds);
			//普通用户
			list = this.permissionService.list(queryWrapper);
		}
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for (Permission p : list) {
			Boolean spread=p.getOpen()==SysConstast.SPREAD_TRUE?true:false;
			nodes.add(new TreeNode(p.getId(), p.getPid(), p.getTitle(), p.getIcon(),p.getHref(),spread, p.getTarget()));
		}
		return TreeNodeBuilder.builder(nodes, 1);
	}
	
	/*********************菜单管理开始*******************/
	/**
	 * 加载菜单管理左边的树
	 */
	@RequestMapping("loadMenuManagerLeftTreeJson")
	public DataGridView loadMenuManagerLeftTreeJson() {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("type", SysConstast.PERMISSION_TYPE_MENU);
		queryWrapper.eq("available", SysConstast.AVAILABLE_TRUE);
		List<Permission> list = permissionService.list(queryWrapper);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for (Permission p : list) {
			Boolean spread=p.getOpen()==SysConstast.SPREAD_TRUE?true:false;
			nodes.add(new TreeNode(p.getId(), p.getPid(), p.getTitle(), p.getIcon(),p.getHref(),spread, p.getTarget()));
		}
		return new DataGridView(nodes);
	}
	
	/**
	 * 记载菜单管理右边的数据
	 */
	@RequestMapping("loadAllMenu")
	public DataGridView loadAllMenu(PermissionVo permissionVo) {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("type", SysConstast.PERMISSION_TYPE_MENU);
		if (StringUtils.isNotBlank(permissionVo.getTitle())) {
			queryWrapper.like("title", permissionVo.getTitle());
		}
		if (permissionVo.getAvailable()!=null) {
			queryWrapper.eq("available", permissionVo.getAvailable());
		}
		if (permissionVo.getId()!=null) {
			queryWrapper.eq("id", permissionVo.getId()).or().eq("pid", permissionVo.getId());
		}
		IPage<Permission> page = new Page<Permission>(permissionVo.getPage(),permissionVo.getLimit());
		permissionService.page(page,queryWrapper);
		return new DataGridView(page.getTotal(),page.getRecords());
		
	}
	
	/**
	 * 查询菜单的最大排序码
	 */
	@RequestMapping("loadMenuMaxOrderNumber")
	public Map<String, Object> loadMenuMaxOrderNumber(){
		Map<String, Object> map = new HashMap<>();
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("ordernum");
		queryWrapper.orderByDesc("ordernum").last("LIMIT 1");
		Permission permission = this.permissionService.getOne(queryWrapper);
		map.put("value", permission.getOrdernum()+1);
		return map;
	}
	
	/**
	 * 添加菜单
	 */
	@RequestMapping("addMenu")
	public ResultObj addMenu(PermissionVo permissionVo) {
		try {
			permissionVo.setType(SysConstast.PERMISSION_TYPE_MENU);
			this.permissionService.save(permissionVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 修改菜单
	 */
	@RequestMapping("updateMenu")
	public ResultObj updateMenu(PermissionVo permissionVo) {
		try {
			permissionService.updateById(permissionVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	/**
	 * 根据当前id查询当前菜单有没有字节点
	 */
	@RequestMapping("checkMenuHasChildren")
	public ResultObj checkMenuHasChildren(Integer id) {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid", id);
		int count = permissionService.count(queryWrapper);
		if (count>0) {
			return ResultObj.STATUS_TRUE;
		}else {
			return ResultObj.STATUS_FALSE;
		}
	}
	
	/**
	 * 删除菜单
	 */
	@RequestMapping("deleteMenu")
	public ResultObj deleteMenu(Integer id) {
		try {
			permissionService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
}

