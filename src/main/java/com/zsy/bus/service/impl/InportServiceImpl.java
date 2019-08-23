package com.zsy.bus.service.impl;

import com.zsy.bus.domain.Goods;
import com.zsy.bus.domain.Inport;
import com.zsy.bus.mapper.GoodsMapper;
import com.zsy.bus.mapper.InportMapper;
import com.zsy.bus.service.IInportService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;

import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-08-16
 */
@Service
@Transactional
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements IInportService {
	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public boolean save(Inport entity) {
		boolean save = super.save(entity);
		// 更新库存
		Goods goods = this.goodsMapper.selectById(entity.getGoodsid());
		goods.setNumber(goods.getNumber() + entity.getNumber());
		goodsMapper.updateById(goods);
		return save;
	}

	@Override
	public boolean updateById(Inport inport) {
		//取出商品
		Inport inport2 = this.getBaseMapper().selectById(inport.getId());
		Goods goods = goodsMapper.selectById(inport2.getGoodsid());
		//新的库存= 原来的库存-进货单原来的进货数量+修改后的数量
		goods.setNumber(goods.getNumber()-inport2.getNumber()+inport.getNumber());
		goodsMapper.updateById(goods);
		boolean updateById = super.updateById(inport);
		return updateById;
	}
	
	@Override
	public boolean removeById(Serializable id) {
		Inport inport = this.getBaseMapper().selectById(id);
		Goods goods = goodsMapper.selectById(inport.getGoodsid());
		goods.setNumber(goods.getNumber()-inport.getNumber());
		goodsMapper.updateById(goods);
		boolean removeById = super.removeById(id);
		return removeById;
	}
}
