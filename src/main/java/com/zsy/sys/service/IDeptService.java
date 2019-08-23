package com.zsy.sys.service;

import com.zsy.sys.domain.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zsy
 * @since 2019-08-10
 */
public interface IDeptService extends IService<Dept> {

	Integer queryDeptMaxOrderNumber();

}
