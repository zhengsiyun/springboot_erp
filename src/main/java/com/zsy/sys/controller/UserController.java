package com.zsy.sys.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.sys.constast.SysConstast;
import com.zsy.sys.domain.Dept;
import com.zsy.sys.domain.Role;
import com.zsy.sys.domain.User;
import com.zsy.sys.service.IDeptService;
import com.zsy.sys.service.IRoleService;
import com.zsy.sys.service.IUserService;
import com.zsy.sys.utils.CacheUtils;
import com.zsy.sys.utils.DataGridView;
import com.zsy.sys.utils.MD5Utils;
import com.zsy.sys.utils.Pinyin4jUtils;
import com.zsy.sys.utils.ResultObj;
import com.zsy.sys.vo.UserVo;

import cn.hutool.core.lang.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsy
 * @since 2019-08-10
 */
@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IDeptService deptService;
	
	@Autowired
	private IRoleService roleService;
	
	
	/**
	 * 加载用户数据
	 */
	@RequestMapping("loadAllUser")
	public DataGridView loadAllUser(UserVo userVo) {
		IPage<User> page = new Page<>(userVo.getPage(), userVo.getLimit());
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.ne("type", SysConstast.USER_TYPE_SUPER);//排除超级管理员
		
		if (StringUtils.isNoneBlank(userVo.getName())) {
			queryWrapper.like("name", userVo.getName());
		}
		if (StringUtils.isNoneBlank(userVo.getAddress())) {
			queryWrapper.like("address", userVo.getAddress());
		}
		if (userVo.getDeptid()!=null) {
			queryWrapper.eq("deptid", userVo.getDeptid());
		}
		IPage<Map<String, Object>> pageMaps = userService.pageMaps(page , queryWrapper);
		long total = page.getTotal();
		List<Map<String, Object>> list = pageMaps.getRecords();
		for (Map<String, Object> map : list) {
			//取出部门
			Object deptIdObj = map.get("deptid");
			//取出领导ID
			Object mgrObj = map.get("mgr");
			//判断缓存里面是否有部门id对应的值
			if (null!=deptIdObj) {
				Integer deptid = (Integer)deptIdObj;
				//去缓存取
				String deptname = CacheUtils.CACHE_MAP.get("dept:"+deptid);
				if (deptname!=null) {
					Dept dept = deptService.getById(deptid);
					//放入缓存
					CacheUtils.CACHE_MAP.put("dept:"+deptid, dept.getTitle());
				}
				deptname=CacheUtils.CACHE_MAP.get("dept:"+deptid);
				//放到页面的map
				map.put("deptname", deptname);
			}
			
			//处理领导的
			if (null!=mgrObj) {
				Integer mgrid = (Integer)mgrObj;
				String leadername = CacheUtils.CACHE_MAP.get("user"+mgrid);
				if (null==leadername) {
					User user = this.userService.getById(mgrid);
					//放入缓存
					CacheUtils.CACHE_MAP.put("user:"+mgrid, user.getName());
				}
				leadername=CacheUtils.CACHE_MAP.get("user:"+mgrid);
				//放到返回页面的map
				map.put("leadername", leadername);
			}
		}
		return new DataGridView(total,list);
	}
	
	/**
	 * 查询用户最大排序码
	 * @return
	 */
	@RequestMapping("loadUserMaxOrderNumber")
	public Map<String, Object> loadUserMaxOrderNumber(){
		Map<String, Object> map = new HashMap<>();
		QueryWrapper<User> queryWrapper =new QueryWrapper<>();
		queryWrapper.select("ordernum");
		queryWrapper.orderByDesc("ordernum").last("LIMIT 1");
		User user = this.userService.getOne(queryWrapper );
		map.put("value", user.getOrdernum()+1);
		return map;
	}
	
	/**
	 * 根据部门id查询用户信息
	 * @param deptid
	 * @return
	 */
	@RequestMapping("loadUserByDeptId")
	public DataGridView loadUserByDeptId(Integer deptid) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("deptid", deptid);
		queryWrapper.eq("available", SysConstast.AVAILABLE_TRUE);
		queryWrapper.ne("type", SysConstast.USER_TYPE_SUPER);
		List<User> list = this.userService.list(queryWrapper);
		return new DataGridView(list);
	}
	
	/**
	 * 把中文转成拼音
	 * @param username
	 * @return
	 */
	@RequestMapping("getUserNameToPinyin")
	public Map<String, Object> getUserNameToPinyin(String username){
		Map<String, Object> map = new HashMap<>();
		map.put("value", Pinyin4jUtils.getPingYin(username));
		return map;
	}
	
	/**
	 * 根据用户id查询用户对象
	 * @param id
	 * @return
	 */
	@RequestMapping("loadUserByUserId")
	public User loadUserByUserId(Integer id) {
		User user = this.userService.getById(id);
		return user;
	}
	
	
	/**
	 * 添加用户
	 * @param userVo
	 * @return
	 */
	@RequestMapping("addUser")
	public ResultObj addUser(UserVo userVo) {
		try {
			//设置类型
			userVo.setType(SysConstast.USER_TYPE_NORMAL);
			//生成盐
			userVo.setSalt(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			//设置密码
			userVo.setPwd(MD5Utils.md5(SysConstast.USER_DEFAULT_PWD, userVo.getSalt()));
			//设置图头像地址
			userVo.setImgpath(SysConstast.DEFAULT_USER_IMG);
			this.userService.save(userVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
		
	}
	
	/**
	 * 修改用户
	 * @param userVo
	 * @return
	 */
	@RequestMapping("updateUser")
	public ResultObj updateUser(UserVo userVo) {
		try {
			this.userService.updateById(userVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	/**
	 *删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteUser")
	public ResultObj deleteUser(Integer id) {
		try {
			this.userService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
	
	/**
	 * 重置密码
	 * @param id
	 * @return
	 */
	@RequestMapping("resetUserPwd")
	public ResultObj resetUserPwd(Integer id) {
		try {
			User user = new User();
			user.setId(id);
			user.setSalt(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			user.setPwd(MD5Utils.md5(SysConstast.USER_DEFAULT_PWD, user.getSalt()));
			userService.updateById(user);
			return ResultObj.RESET_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.RESET_ERROR;
		}
	}
	
	/**
	 * 根据用户id查询角色 让拥有的角色选中
	 * @param id
	 * @return
	 */
	@RequestMapping("initUserRoles")
	public DataGridView initUserRoles(Integer id) {
		//查询所有角色
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("available", SysConstast.AVAILABLE_TRUE);
		List<Role> allRoles = roleService.list(queryWrapper);
		//根据当前用户ID查询当前用户拥有那个角色id
		List<Integer> currentUserHasRoleIds = this.roleService.queryRoleUserByUserId(id);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (Role role : allRoles) {
			Map<String, Object> map = new HashMap<>();
			Boolean LAY_CHECKED = false;
			if (currentUserHasRoleIds!=null&&currentUserHasRoleIds.size()>0) {
				for (Integer integer : currentUserHasRoleIds) {
					if (role.getId()==integer) {
						LAY_CHECKED=true;
						break;
					}
				}
			}
			map.put("id", role.getId());
			map.put("name", role.getName());
			map.put("remark", role.getRemark());
			map.put("available", role.getAvailable());
			map.put("LAY_CHECKED", LAY_CHECKED);
			list.add(map);
		}
		return new DataGridView(list);
	}
	
	/**
	 * 保存用户和角色的关系
	 * @param userVo
	 * @return
	 */
	@RequestMapping("saveUserRole")
	public ResultObj saveUserRole(UserVo userVo) {
		try {
			this.userService.saveUserRole(userVo.getId(),userVo.getIds());
			return ResultObj.DISPATCH_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DISPATCH_ERROR;
		}
	}
	
}
