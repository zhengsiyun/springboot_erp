package com.zsy.bus.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.bus.domain.Goods;
import com.zsy.bus.domain.Inport;
import com.zsy.bus.domain.Provider;
import com.zsy.bus.service.IGoodsService;
import com.zsy.bus.service.IInportService;
import com.zsy.bus.service.IProviderService;
import com.zsy.bus.vo.InportVo;
import com.zsy.sys.domain.User;
import com.zsy.sys.utils.DataGridView;
import com.zsy.sys.utils.ResultObj;
import com.zsy.sys.utils.WebUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsy
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/inport")
public class InportController {
	
	@Autowired
	private IInportService inportService;
	
	@Autowired
	private IProviderService providerService;
	
	@Autowired
	private IGoodsService goodsService;
	
	/**
	 * 查询所有进货信息
	 */
	@RequestMapping("loadAllInport")
	public DataGridView loadAllInport(InportVo inportVo) {
		IPage<Inport> page = new Page<>(inportVo.getPage(), inportVo.getLimit());
		
		QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();
		if (inportVo.getProviderid()!=null) {
			queryWrapper.eq("providerid", inportVo.getProviderid());
		}
		if (inportVo.getGoodsid()!=null) {
			queryWrapper.eq("goodsid", inportVo.getGoodsid());
		}
		if (inportVo.getStartTime()!=null) {
			queryWrapper.ge("inporttime", inportVo.getStartTime());
		}
		if (inportVo.getEndTime()!=null) {
			queryWrapper.le("inporttime", inportVo.getEndTime());
		}
		//排序
		queryWrapper.orderByDesc("inporttime");
		IPage<Map<String, Object>> maps = inportService.pageMaps(page,queryWrapper);
		List<Map<String, Object>> records = maps.getRecords();
		for (Map<String, Object> map : records) {
			Integer goodsid = (Integer) map.get("goodsid");
			if (goodsid!=null) {
				Goods goods = goodsService.getById(goodsid);
				map.put("goodsname", goods.getGoodsname());
				map.put("size", goods.getSize());
			}
			Integer providerid = (Integer) map.get("providerid");
			if (providerid!=null) {
				Provider provider = providerService.getById(providerid);
				map.put("providername", provider.getProvidername());
			}
		}
		return new DataGridView(page.getTotal(), records);
	}
	
	
	/**
	 * 添加
	 * @param inportVo
	 * @return
	 */
	@RequestMapping("addInport")
	public ResultObj addInport(InportVo inportVo) {
		try {
			User user = (User) WebUtils.getHttpSession().getAttribute("user");
			//设置创建时间
			inportVo.setInporttime(new Date());
			//设置操作人
			inportVo.setOperateperson(user.getName());
			this.inportService.save(inportVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 更新进货商品
	 * @param inportVo
	 * @return
	 */
	@RequestMapping("updateInport")
	public ResultObj updateInport(InportVo inportVo) {
		try {
			inportService.updateById(inportVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	
	/**
	 * 删除进货商品信息
	 * @param inportVo
	 * @return
	 */
	@RequestMapping("deleteInport")
	public ResultObj deleteInport(Integer id) {
		try {
			inportService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
}

