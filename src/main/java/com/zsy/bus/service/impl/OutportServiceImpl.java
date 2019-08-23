package com.zsy.bus.service.impl;

import com.zsy.bus.domain.Goods;
import com.zsy.bus.domain.Outport;
import com.zsy.bus.mapper.GoodsMapper;
import com.zsy.bus.mapper.OutportMapper;
import com.zsy.bus.service.IOutportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-08-16
 */
@Service
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements IOutportService {
		
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Override
	public boolean save(Outport entity) {
		boolean save = super.save(entity);
		Goods goods = goodsMapper.selectById(entity.getGoodsid());
		goods.setNumber(goods.getNumber()-entity.getNumber());
		goodsMapper.updateById(goods);
		return save;
	}
}
