package com.zsy.bus.controller;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.bus.domain.Goods;
import com.zsy.bus.domain.Provider;
import com.zsy.bus.service.IGoodsService;
import com.zsy.bus.service.IProviderService;
import com.zsy.bus.vo.GoodsVo;
import com.zsy.sys.constast.SysConstast;
import com.zsy.sys.utils.AppFileUtils;
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
@RequestMapping("/goods")
public class GoodsController {

		@Autowired
		private IGoodsService goodsService;
		
		@Autowired
		private IProviderService providerService;
		
		/**
		 * 查询所有商品分页
		 */
		@RequestMapping("loadAllGoods")
		public DataGridView loadAllGoods(GoodsVo goodsVo) {
			IPage<Goods> page = new Page<>(goodsVo.getPage(),goodsVo.getLimit());
			QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
			if (null!=goodsVo.getProviderid()) {
				queryWrapper.eq("providerid", goodsVo.getProviderid());
			}
			if (StringUtils.isNotBlank(goodsVo.getGoodsname())) {
				queryWrapper.like("goodsname", goodsVo.getGoodsname());
				
			}
			if (StringUtils.isNotBlank(goodsVo.getProductcode())) {
				queryWrapper.like("productcode", goodsVo.getProductcode());
				
			}
			if (StringUtils.isNotBlank(goodsVo.getPromitcode())) {
				queryWrapper.like("promitcode", goodsVo.getPromitcode());
				
			}
			if (StringUtils.isNotBlank(goodsVo.getDescription())) {
				queryWrapper.like("description", goodsVo.getDescription());
				
			}
			if (StringUtils.isNotBlank(goodsVo.getSize())) {
				queryWrapper.like("size", goodsVo.getSize());
				
			}
			IPage<Map<String, Object>> pageMaps = this.goodsService.pageMaps(page ,queryWrapper);
			
			List<Map<String, Object>> records = pageMaps.getRecords();
			for (Map<String, Object> map : records) {
					Object object = map.get("providerid");
					Provider provider = providerService.getById((Integer)object);
					map.put("providername", provider.getProvidername());
			}
			return new DataGridView(page.getTotal(),records);
		}
		
		/**
		 * 添加商品
		 */
		@RequestMapping("addGoods")
		public ResultObj addGodds(GoodsVo goodsVo) {
			try {
				//如果不是默认图片就去掉图片的_temp的后缀
				if(!goodsVo.getGoodsimg().equals(SysConstast.DEFAULT_GOODS_IMG)) {
					String filePath=AppFileUtils.updateFileName(goodsVo.getGoodsimg(),SysConstast.FILE_UPLOAD_TEMP);
					goodsVo.setGoodsimg(filePath);
				}
				this.goodsService.save(goodsVo);
				return ResultObj.ADD_SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ResultObj.ADD_ERROR;
			}
		}
		
		/**
		 * 修改商品
		 */
		@RequestMapping("updateGoods")
		public ResultObj updateGoods(GoodsVo goodsVo) {
			try {
				String carimg = goodsVo.getGoodsimg();
				if(carimg.endsWith(SysConstast.FILE_UPLOAD_TEMP)) {
					String filePath=AppFileUtils.updateFileName(goodsVo.getGoodsimg(),SysConstast.FILE_UPLOAD_TEMP);
					goodsVo.setGoodsimg(filePath);
					//把原来的删除
					Goods goods=this.goodsService.getById(goodsVo.getId());
					AppFileUtils.removeFileByPath(goods.getGoodsimg());
				}
				this.goodsService.updateById(goodsVo);
				return ResultObj.UPDATE_SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ResultObj.UPDATE_ERROR;
			}
		}
		
		/**
		 * 删除商品
		 */
		@RequestMapping("deleteGoods")
		public ResultObj deleteGoods(GoodsVo goodsVo) {
			try {
				//删除图片
				Goods goods=this.goodsService.getById(goodsVo.getId());
				if(!goods.getGoodsimg().equals(SysConstast.DEFAULT_GOODS_IMG)) {
					AppFileUtils.removeFileByPath(goods.getGoodsimg());
				}
				this.goodsService.removeById(goodsVo.getId());
				return ResultObj.DELETE_SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ResultObj.DELETE_ERROR;
			}
		}
		
		/*
		 * 查询所有可用的商品
		 */
		@RequestMapping("loadAllAvailableGoodsJson")
		public List<Goods> loadAllAvailableGoodsJson(){
			QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("available", SysConstast.AVAILABLE_TRUE);
			return this.goodsService.list(queryWrapper);
		}
}
