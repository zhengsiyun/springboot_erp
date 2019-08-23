package com.zsy.sys.mapper;

import com.zsy.sys.domain.Role;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zsy
 * @since 2019-08-12
 */
public interface RoleMapper extends BaseMapper<Role> {
	
	/**
	 * 根据角色id删除关联表里的数据
	 * @param id
	 */
	@Delete("delete from sys_role_permission where rid=#{value}")
	void deleteRolePermissionByRid(Serializable id);
	
	/**
	 * 根据角色id查询所有菜单
	 * @param id
	 * @return
	 */
	@Select("select pid from sys_role_permission where rid=#{value}")
	List<Integer> queryAllPermissionByRid(Integer id);
	
	/**
	 * 添加所有保存的角色和权限的关系
	 * @param rid
	 * @param pid
	 */
	@Insert("insert into sys_role_permission(rid,pid) values(#{rid},#{pid})")
	void insertRolePermission(@Param("rid")Integer rid, @Param("pid")Integer pid);
	
	/**
	 * 根据当前用户id  查询用户拥有哪个角色id
	 * @param id
	 * @return
	 */
	@Select("select rid from sys_role_user where uid=#{value}")
	List<Integer> queryRoleUserByUserId(Integer id);
	
	/**
	 * 根据用户ID查询权限编码
	 * @param roleids
	 * @return
	 */
	List<String> queryRoleNameByIds(List<Integer> roleids);
	
	/**
	 * 根据角色id删除sys_role_user
	 * @param id
	 */
	@Delete("delete from sys_role_user where rid=#{rid}")
	void deleteRoleUserByRoleId(Serializable id);


}
