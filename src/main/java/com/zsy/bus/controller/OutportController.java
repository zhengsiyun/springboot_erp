package com.zsy.bus.controller;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.bus.domain.Goods;
import com.zsy.bus.domain.Inport;
import com.zsy.bus.domain.Outport;
import com.zsy.bus.domain.Provider;
import com.zsy.bus.service.IGoodsService;
import com.zsy.bus.service.IInportService;
import com.zsy.bus.service.IOutportService;
import com.zsy.bus.service.IProviderService;
import com.zsy.bus.vo.OutportVo;
import com.zsy.sys.domain.User;
import com.zsy.sys.utils.DataGridView;
import com.zsy.sys.utils.ResultObj;
import com.zsy.sys.utils.WebUtils;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsy
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/outport")
public class OutportController {
	
	@Autowired
	private IInportService inportService;
	
	@Autowired
	private IOutportService outportService;
	
	@Autowired
	private IGoodsService goodsService;
	
	@Autowired
	private IProviderService providerService;
	
	@RequestMapping("addOutPort")
	private ResultObj addOutport(OutportVo outportVo) {
		try {
			User user = (User) WebUtils.getHttpSession().getAttribute("user");
			//根据outportVo里面的id查询出进货数据
			Inport inport = inportService.getById(outportVo.getId());
			outportVo.setProviderid(inport.getProviderid());
			outportVo.setPaytype(inport.getPaytype());
			outportVo.setOutputtime(new Date());
			outportVo.setOperateperson(user.getName());
			outportVo.setOutportprice(inport.getInportprice());
			outportVo.setRemark(inport.getRemark());
			outportVo.setGoodsid(inport.getGoodsid());
			outportService.save(outportVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	

	/**
	 * 查询所有进货信息
	 */
	@RequestMapping("loadAllOutport")
	public DataGridView loadAllOutport(OutportVo outportVo) {
		IPage<Outport> page=new Page<>(outportVo.getPage(), outportVo.getLimit());
		QueryWrapper<Outport> queryWrapper=new QueryWrapper<>();
		if(outportVo.getProviderid()!=null) {
			queryWrapper.eq("providerid", outportVo.getProviderid());
		}
		if(outportVo.getGoodsid()!=null) {
			queryWrapper.eq("goodsid", outportVo.getGoodsid());
		}
		if(outportVo.getStartTime()!=null) {
			queryWrapper.ge("outputtime", outportVo.getStartTime());
		}
		if(outportVo.getEndTime()!=null) {
			queryWrapper.le("outputtime", outportVo.getEndTime());
		}
		//排序
		queryWrapper.orderByDesc("outputtime");
		IPage<Map<String,Object>> maps = outportService.pageMaps(page, queryWrapper);
		List<Map<String,Object>> records = maps.getRecords();
		for (Map<String, Object> map : records) {
			Integer goodsId=(Integer) map.get("goodsid");
			if(goodsId!=null) {
				Goods goods = goodsService.getById(goodsId);
				map.put("goodsname", goods.getGoodsname());
				map.put("size", goods.getSize());
			}
			Integer providerId=(Integer) map.get("providerid");
			if(providerId!=null) {
				Provider provider = this.providerService.getById(providerId);
				map.put("providername", provider.getProvidername());
			}
		}
		return new DataGridView(page.getTotal(),records);
	}
	
}
