package com.zsy.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sys")
public class SysController {
	
	@RequestMapping("toDeskManager")
	public String toDeskManager() {
		return "system/main/deskManager";
	}
	
	
	/**
	 * 跳转部门管理
	 */
	@RequestMapping("toDeptManager")
	public String toDeptManager() {
		return "system/dept/deptManager";
	}
	
	/**
	 * 跳转部门管理左边的的部门树页面
	 */
	@RequestMapping("toDeptLeft")
	public String toDeptLeft() {
		return "system/dept/deptLeft";
	}
	/**
	 * 跳转部门管理右边的部门列表
	 */
	@RequestMapping("toDeptRight")
	public String toDeptRight() {
		return "system/dept/deptRight";
	}
	
	/**
	 * 跳转菜单管理
	 */
	@RequestMapping("toMenuManager")
	public String toMenuManager() {
		return "system/menu/menuManager";
	}
	
	/**
	 * 跳转菜单管理左边的的菜单树页面
	 */
	@RequestMapping("toMenuLeft")
	public String toMenuLeft() {
		return "system/menu/menuLeft";
	}
	/**
	 * 跳转菜单管理右边的菜单列表
	 */
	@RequestMapping("toMenuRight")
	public String toMenuRight() {
		return "system/menu/menuRight";
	}
	
	/**
	 * 跳转权限管理
	 */
	@RequestMapping("toPermissionManager")
	public String toPermissionManager() {
		return "system/permission/permissionManager";
	}
	
	/**
	 * 跳转权限管理左边的的权限树页面
	 */
	@RequestMapping("toPermissionLeft")
	public String toPermissionLeft() {
		return "system/permission/permissionLeft";
	}
	/**
	 * 跳转权限管理右边的权限列表
	 */
	@RequestMapping("toPermissionRight")
	public String toPermissionRight() {
		return "system/permission/permissionRight";
	}
	
	/**
	 * 查询角色管理
	 */
	@RequestMapping("toRoleManager")
	public String toRoleManager() {
		return "system/role/roleManager";
	}
	
	/**
	 * 跳转用户管理
	 */
	@RequestMapping("toUserManager")
	public String toUserManager() {
		return "system/user/userManager";
	}
	
	/**
	 * 跳转用户管理左边的的用户树页面
	 */
	@RequestMapping("toUserLeft")
	public String toUserLeft() {
		return "system/user/userLeft";
	}
	/**
	 * 跳转用户管理右边的用户列表
	 */
	@RequestMapping("toUserRight")
	public String toUserRight() {
		return "system/user/userRight";
	}
	
	/**
	 * 跳转登陆日志管理列表
	 */
	@RequestMapping("toLogLoginManager")
	public String toLogLoginManager() {
		return "system/loglogin/logloginManager";
	}
	/**
	 * 跳转系统公告管理列表
	 */
	@RequestMapping("toNoticeManager")
	public String toNoticeManager() {
		return "system/notice/noticeManager";
	}
}

