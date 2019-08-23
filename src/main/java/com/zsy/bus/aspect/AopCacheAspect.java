package com.zsy.bus.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.zsy.bus.domain.Goods;
import com.zsy.bus.domain.Provider;

@Component
@Aspect
@EnableAspectJAutoProxy
public class AopCacheAspect {
	
	private static Map<String, Object> CACHEMAP = new HashMap<String, Object>();
	
	private static final String GOODS_PREFIX = "goods:";
	
	/**
	 * 商品缓存处理
	 */
	private static final String GOODS_QUERY_EXECUTIONS = "execution(* com.zsy.bus.service.impl.GoodsServiceImpl.getById(..))";
	private static final String GOODS_UPDATE_EXECUTIONS = "execution(* com.zsy.bus.service.impl.GoodsServiceImpl.updateById(..))";
	private static final String GOODS_DELETE_EXECUTIONS = "execution(* com.zsy.bus.service.impl.GoodsServiceImpl.removeById(..))";
	
	
	/**
	 * 查询缓存
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value=GOODS_QUERY_EXECUTIONS)
	public Object queryGoodsAspect(ProceedingJoinPoint joinPoint) throws Throwable{
		//拿到参数
		Integer goodsId = (Integer) joinPoint.getArgs()[0];
		if (null==goodsId) {
			return null;
		}else {
			//先到缓存查询
			Object object = CACHEMAP.get(GOODS_PREFIX+goodsId);
			if (null!=object) {
				System.out.println("已从缓存中拿到,不到数据库查:"+GOODS_PREFIX+goodsId);
				return object;
			}else {
				Object proceed = joinPoint.proceed();
				CACHEMAP.put(GOODS_PREFIX+goodsId, proceed);
				System.out.println("缓存添加成功:"+GOODS_PREFIX+goodsId);
				return proceed;
			}
		}
	}
	
	/**
	 * 缓存更新
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value=GOODS_UPDATE_EXECUTIONS)
	public Object updateGoodsAspect(ProceedingJoinPoint joinPoint) throws Throwable{
		Boolean proceed = (Boolean) joinPoint.proceed();//执行目标方法
		if (proceed) {
			Goods goods = (Goods) joinPoint.getArgs()[0];
			//覆盖缓存
			CACHEMAP.put(GOODS_PREFIX+goods.getId(), goods);
			System.out.println("缓存更新成功:"+GOODS_PREFIX+goods.getId());
		}
		return proceed;
	}
	
	/**
	 * 缓存删除
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value=GOODS_DELETE_EXECUTIONS)
	public Object deleteGoodsAspect(ProceedingJoinPoint joinPoint) throws Throwable{
		Boolean proceed = (Boolean) joinPoint.proceed();//执行目标方法
		if (proceed) {
			Integer goodsId= (Integer) joinPoint.getArgs()[0];
			//覆盖缓存
			CACHEMAP.remove(GOODS_PREFIX+goodsId);
			System.out.println("缓存更新成功:"+GOODS_PREFIX+goodsId);
		}
		return proceed;
	}
	
	
	
	

	private static final String PROVIDER_PREFIX = "provider:";
	
	/**
	 * 商品缓存处理
	 */
	private static final String  PROVIDER_QUERY_EXECUTIONS = "execution(* com.zsy.bus.service.impl.ProviderServiceImpl.getById(..))";
	private static final String PROVIDER_UPDATE_EXECUTIONS = "execution(* com.zsy.bus.service.impl.ProviderServiceImpl.updateById(..))";
	private static final String PROVIDER_DELETE_EXECUTIONS = "execution(* com.zsy.bus.service.impl.ProviderServiceImpl.removeById(..))";
	
	
	/**
	 * 查询缓存
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value=PROVIDER_QUERY_EXECUTIONS)
	public Object queryProviderAspect(ProceedingJoinPoint joinPoint) throws Throwable{
		//拿到参数
		Integer providerId = (Integer) joinPoint.getArgs()[0];
		if (null==providerId) {
			return null;
		}else {
			//先到缓存查询
			Object object = CACHEMAP.get(PROVIDER_PREFIX+providerId);
			if (null!=object) {
				System.out.println("已从缓存中拿到,不到数据库查:"+PROVIDER_PREFIX+providerId);
				return object;
			}else {
				Object proceed = joinPoint.proceed();
				CACHEMAP.put(PROVIDER_PREFIX+providerId, proceed);
				System.out.println("缓存添加成功:"+PROVIDER_PREFIX+providerId);
				return proceed;
			}
		}
	}
	
	/**
	 * 缓存更新
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value=PROVIDER_UPDATE_EXECUTIONS)
	public Object updateProviderAspect(ProceedingJoinPoint joinPoint) throws Throwable{
		Boolean proceed = (Boolean) joinPoint.proceed();//执行目标方法
		if (proceed) {
			Provider provider = (Provider) joinPoint.getArgs()[0];
			//覆盖缓存
			CACHEMAP.put(PROVIDER_PREFIX+provider.getId(), provider);
			System.out.println("缓存更新成功:"+PROVIDER_PREFIX+provider.getId());
		}
		return proceed;
	}
	
	/**
	 * 缓存删除
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value=PROVIDER_DELETE_EXECUTIONS)
	public Object deleteProviderAspect(ProceedingJoinPoint joinPoint) throws Throwable{
		Boolean proceed = (Boolean) joinPoint.proceed();//执行目标方法
		if (proceed) {
			Integer providerId= (Integer) joinPoint.getArgs()[0];
			//覆盖缓存
			CACHEMAP.remove(PROVIDER_PREFIX+providerId);
			System.out.println("缓存更新成功:"+PROVIDER_PREFIX+providerId);
		}
		return proceed;
	}
}
