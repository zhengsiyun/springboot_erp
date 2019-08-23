package com.zsy.sys.mapper;

import com.zsy.sys.domain.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zsy
 * @since 2019-08-10
 */
public interface DeptMapper extends BaseMapper<Dept> {

	Integer queryDeptMaxOrderNumber();

}
