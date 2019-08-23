package com.zsy.bus.service.impl;

import com.zsy.bus.domain.Goods;
import com.zsy.bus.mapper.GoodsMapper;
import com.zsy.bus.service.IGoodsService;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-08-14
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
	@Override
	public Goods getById(Serializable id) {
		return super.getById(id);
	}
	
	@Override
	public boolean save(Goods entity) {
		return super.save(entity);
	}
	
	@Override
	public boolean updateById(Goods entity) {
		return super.updateById(entity);
	}
	
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}
}
