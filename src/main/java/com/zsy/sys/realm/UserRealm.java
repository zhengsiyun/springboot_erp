package com.zsy.sys.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsy.sys.constast.SysConstast;
import com.zsy.sys.domain.User;
import com.zsy.sys.service.IPermissionService;
import com.zsy.sys.service.IRoleService;
import com.zsy.sys.service.IUserService;
import com.zsy.sys.utils.ActiverUser;

public class UserRealm extends AuthorizingRealm{
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IPermissionService permissionService;
	
	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = token.getPrincipal().toString();
		//根据登陆名查询用户
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("loginname", username);
		User user = userService.getOne(queryWrapper);
		if (null!=user) {
			ActiverUser activerUser = new ActiverUser();
			if (user.getType()!=SysConstast.USER_TYPE_SUPER) {
				//查询角色
				activerUser.setRoles(this.roleService.queryRoleNameUserByUserId(user.getId()));
				//查询权限
				activerUser.setPermissions(this.permissionService.queryPermissionCodeByUserId(user.getId()));
			}
			activerUser.setUser(user);
			ByteSource salt = ByteSource.Util.bytes(user.getSalt());//得到盐
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser,user.getPwd(),salt,getName());
			return info;
		}
		return null;
	}
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ActiverUser activerUser = (ActiverUser) principals.getPrimaryPrincipal();
		List<String> roles = activerUser.getRoles();
		List<String> permissions = activerUser.getPermissions();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//如果超级管理员为0 拥有所有权限和菜单
		if (activerUser.getUser().getType()==SysConstast.USER_TYPE_SUPER) {
			info.addStringPermission("*:*");
		}else {
			if (null!=roles&&roles.size()>0) {
				info.addRoles(roles);
			}
			
			if (null!=permissions&&permissions.size()>0) {
				info.addStringPermissions(permissions);
			}
		}
		return info;
	}

	

}
