package com.zsy.bus.controller;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.bus.domain.Provider;
import com.zsy.bus.service.IProviderService;
import com.zsy.bus.vo.ProviderVo;
import com.zsy.sys.constast.SysConstast;
import com.zsy.sys.utils.DataGridView;
import com.zsy.sys.utils.ResultObj;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsy
 * @since 2019-08-14
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {
	

	@Autowired
	private IProviderService providerService;
	
	/**
	 * 查询全部供应商
	 * @return
	 */
	@RequestMapping("loadAllProvider")
	public DataGridView loadAllProvider(ProviderVo providerVo) {
		IPage<Provider> page = new Page<>();
		QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNoneBlank(providerVo.getProvidername())) {
			queryWrapper.like("providername", providerVo.getProvidername());
		}
		if (StringUtils.isNoneBlank(providerVo.getConnectionperson())) {
			queryWrapper.like("connectionperson", providerVo.getConnectionperson());
		}
		if (StringUtils.isNoneBlank(providerVo.getTelephone())) {
			queryWrapper.like("telephone", providerVo.getTelephone());
		}
		providerService.page(page ,queryWrapper );
		return new DataGridView(page.getTotal(),page.getRecords());
	}
	
	/**
	 * 添加
	 * @param providerVo
	 * @return
	 */
	@RequestMapping("addProvider")
	public ResultObj addProvider(ProviderVo providerVo) {
		try {
			this.providerService.save(providerVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
		
	}
	
	/**
	 * 修改
	 * @param providerVo
	 * @return
	 */
	@RequestMapping("updateProvider")
	public ResultObj updateProvider(ProviderVo providerVo) {
		try {
			this.providerService.updateById(providerVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteProvider")
	public ResultObj deleteProvider(Integer id) {
		try {
			this.providerService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
	
	/**
	 * 查询所有供应商返回集合
	 * @param providerVo
	 * @return
	 */
	@RequestMapping("loadAllAvailableProviderJson")
	public List<Provider> loadAllAvailableProviderJson(ProviderVo providerVo){
		QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("available", SysConstast.AVAILABLE_TRUE);
		List<Provider> list = this.providerService.list(queryWrapper);
		return list;
	}
}
