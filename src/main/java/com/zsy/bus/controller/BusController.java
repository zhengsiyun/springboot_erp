package com.zsy.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.bytebuddy.asm.Advice.This;

@Controller
@RequestMapping("bus")
public class BusController {
	
	/*
	 * 跳转到客户管理
	 */
	@RequestMapping("toCustomerManager")
	public String toCustomerManager() {
		return "business/customer/customerManager";
	}
	
	
	/**
	 * 跳转到供应商管理
	 * @return
	 */
	@RequestMapping("toProviderManager")
	public String toProviderManager() {
		return "business/provider/providerManager";
	}
	
	/**
	 * 跳转到供应商管理
	 * @return
	 */
	@RequestMapping("toGoodsManager")
	public String toGoodsManager() {
		return "business/goods/goodsManager";
	}
	
	/**
	 * 跳转到商品进货管理
	 * @return
	 */
	@RequestMapping("toInportManager")
	public String toInportManager() {
		return "business/inport/inportManager";
	}
	
	/**
	 * 跳转到商品进货管理
	 * @return
	 */
	@RequestMapping("toOutportManager")
	public String toOutportManager() {
		return "business/outport/outportManager";
	}
	
}
