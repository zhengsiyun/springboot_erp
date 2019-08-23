package com.zsy.sys.mapper;

import com.zsy.sys.domain.User;

import java.io.Serializable;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zsy
 * @since 2019-08-10
 */
public interface UserMapper extends BaseMapper<User> {
	
	/***
	 * 根据用户id删除sys_role_user表里的数据
	 * @param id
	 */
	@Delete("delete from sys_role_user where uid=#{value}")
	void deleteUserRoleByUserId(Serializable id);
	/**
	 * 保存用户和角色的关系
	 * @param userid
	 * @param rid
	 */
	@Insert("insert into sys_role_user(uid,rid) values(#{uid},#{rid})")
	void insertUserRole(@Param("uid")Integer userid, @Param("rid")Integer rid);

}
