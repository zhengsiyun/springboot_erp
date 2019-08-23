package com.zsy.sys.service.impl;

import com.zsy.sys.domain.User;
import com.zsy.sys.mapper.UserMapper;
import com.zsy.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	
	@Override
	public boolean removeById(Serializable id) {
		//根据id删除sys_role_user里的数据
		this.getBaseMapper().deleteUserRoleByUserId(id);
		boolean removeById = super.removeById(id);
		return removeById;
	}

	@Override
	public void saveUserRole(Integer userid, Integer[] ids) {
		//根据用户id删除sys_role_user里的数据
		this.getBaseMapper().deleteUserRoleByUserId(userid);
		//保存用户和角色的关系
		if (null!=ids&&ids.length>0) {
			for (Integer rid : ids) {
				this.getBaseMapper().insertUserRole(userid,rid);
			}
		}
		
		
	}
}
