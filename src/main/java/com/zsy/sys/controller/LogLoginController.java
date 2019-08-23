package com.zsy.sys.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.sys.domain.LogLogin;
import com.zsy.sys.service.ILogLoginService;
import com.zsy.sys.utils.DataGridView;
import com.zsy.sys.utils.ResultObj;
import com.zsy.sys.vo.LogLoginVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsy
 * @since 2019-08-13
 */
@RestController
@RequestMapping("/logLogin")
public class LogLoginController {
	
	@Autowired
	private ILogLoginService logLoginService;
	
	/**
	 * 查询所有日志
	 * @param logLoginVo
	 * @return
	 */
	@RequestMapping("loadAllLogLogin")
	public DataGridView loadAllLogLogin(LogLoginVo logLoginVo) {
		IPage<LogLogin> page = new Page<>(logLoginVo.getPage(),logLoginVo.getLimit());
		
		QueryWrapper<LogLogin> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(logLoginVo.getLoginname())) {
			queryWrapper.like("loginname", logLoginVo.getLoginname());
		}
		if (StringUtils.isNotBlank(logLoginVo.getLoginip())) {
			queryWrapper.like("loginip", logLoginVo.getLoginip());
		}
		if (null!=logLoginVo.getStartDate()) {
			queryWrapper.ge("logintime", logLoginVo.getStartDate());
		}
		if (null!=logLoginVo.getEndDate()) {
			queryWrapper.le("logintime", logLoginVo.getEndDate());
		}
		queryWrapper.orderByDesc("logintime");
		this.logLoginService.page(page ,queryWrapper);
		return new DataGridView(page.getTotal(),page.getRecords());
	}
	
	@RequestMapping("deleteLogLogin")
	public ResultObj deleteLogLogin(Integer id) {
		try {
			this.logLoginService.removeById(id);
			return 	ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
}
