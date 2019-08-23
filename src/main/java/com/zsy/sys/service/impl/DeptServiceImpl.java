package com.zsy.sys.service.impl;

import com.zsy.sys.domain.Dept;
import com.zsy.sys.mapper.DeptMapper;
import com.zsy.sys.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
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
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {
	@Autowired
	private DeptMapper deptMapper;
	@Override
	public Integer queryDeptMaxOrderNumber() {
		
		return deptMapper.queryDeptMaxOrderNumber();
	}

}
