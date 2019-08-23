package com.zsy.sys.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.zsy.sys.realm.UserRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import lombok.Data;

/**
 * shiro的配置类
 * @author zsy
 *
 */
@Configuration
@Data
@ConfigurationProperties("shiro")
public class ShiroAutoConfiguration {
	
	private static final String SHIRO_FILTER = "shiroFilter";
	private String hashAlgorithmName;
	private int hashIterations;
	
	private String loginUrl="/login/toLogin";
	private String unauthorizedUrl;
	
	private String [] anonUrl;
	
	private String  logOutUrl;
	
	private String [] authcUrl;
	
	
	
	/**
	 * 创建凭证匹配器
	 */
	@Bean("credentialsMatcher")
	public HashedCredentialsMatcher getCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		//注入算法和散列次数
		credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
		credentialsMatcher.setHashIterations(hashIterations);
		return credentialsMatcher;
	}
	
	
	/**
	 * 声明realm
	 */
	@Bean("userRealm")
	public UserRealm getUserRealm(HashedCredentialsMatcher credentialsMatcher) {
		UserRealm userRealm = new UserRealm();
		userRealm.setCredentialsMatcher(credentialsMatcher);
		return userRealm;
	}
	
	/**
	 * 创建安全管理器
	 */
	@Bean("securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		return securityManager;
	}
	
	
	/**
	 * 声明shiroFilterFactoryBean
	 */
	@Bean(SHIRO_FILTER)
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		//登陆页面
		factoryBean.setLoginUrl(loginUrl);
		//shiro的核心安全接口
		factoryBean.setSecurityManager(securityManager);
		//用户访问未对授权的资源  显示的连接
		factoryBean.setUnauthorizedUrl(unauthorizedUrl);
		Map<String, String> filterChain = new HashMap<String, String>();
		//注入放行的路径
		if (null!=anonUrl&&anonUrl.length>0) {
			for (String url : anonUrl) {
				filterChain.put(url, "anon");
				
			}
		}
		//注入退出的路径
		filterChain.put(logOutUrl, "logout");
		//注入拦截的路径
		if (null!=authcUrl&&authcUrl.length>0) {
			for (String url : authcUrl) {
				filterChain.put(url, "authc");
			}
		}
		//注入过滤器链
		factoryBean.setFilterChainDefinitionMap(filterChain);
		return factoryBean;
	}
	
	/**
	 * //加入注解的使用，不加入这个注解不生效
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 注册shiro的委托过滤器，相当于之前在web.xml里面配置的
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
		FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName(SHIRO_FILTER);
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}

	// 这里是为了能在html页面引用shiro标签，上面两个函数必须添加，不然会报错
	@Bean(name = "shiroDialect")
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

}

