package com.zsy.sys.service;

import com.zsy.sys.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zsy
 * @since 2019-08-10
 */
public interface IUserService extends IService<User> {

	void saveUserRole(Integer id, Integer[] ids);

}
