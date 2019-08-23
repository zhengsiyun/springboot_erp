package com.zsy.bus.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.bus.domain.Goods;
import com.zsy.bus.domain.Provider;
import com.zsy.bus.mapper.ProviderMapper;
import com.zsy.bus.service.IProviderService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-08-14
 */
@Service
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements IProviderService {
	
	@Override
	public Provider getById(Serializable id) {
		return super.getById(id);
	}
	
	
	@Override
	public boolean updateById(Provider entity) {
		return super.updateById(entity);
	}
	
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}
}

