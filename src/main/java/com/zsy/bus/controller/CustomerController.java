package com.zsy.bus.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.bus.domain.Customer;
import com.zsy.bus.service.ICustomerService;
import com.zsy.bus.vo.CustomerVo;
import com.zsy.sys.utils.DataGridView;
import com.zsy.sys.utils.ResultObj;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsy
 * @since 2019-08-14
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private ICustomerService customerService;
	
	/**
	 * 查询全部顾客
	 * @return
	 */
	@RequestMapping("loadAllCustomer")
	public DataGridView loadAllCustomer(CustomerVo customerVo) {
		IPage<Customer> page = new Page<>();
		QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNoneBlank(customerVo.getCustomername())) {
			queryWrapper.like("customername", customerVo.getCustomername());
		}
		if (StringUtils.isNoneBlank(customerVo.getConnectionperson())) {
			queryWrapper.like("connectionperson", customerVo.getConnectionperson());
		}
		if (StringUtils.isNoneBlank(customerVo.getTelephone())) {
			queryWrapper.like("telephone", customerVo.getTelephone());
		}
		customerService.page(page ,queryWrapper );
		return new DataGridView(page.getTotal(),page.getRecords());
	}
	
	/**
	 * 添加
	 * @param customerVo
	 * @return
	 */
	@RequestMapping("addCustomer")
	public ResultObj addCustomer(CustomerVo customerVo) {
		try {
			this.customerService.save(customerVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
		
	}
	
	/**
	 * 修改
	 * @param customerVo
	 * @return
	 */
	@RequestMapping("updateCustomer")
	public ResultObj updateCustomer(CustomerVo customerVo) {
		try {
			this.customerService.updateById(customerVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	
	/**
	 * 添加
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteCustomer")
	public ResultObj deleteCustomer(Integer id) {
		try {
			this.customerService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
		
	}
}

