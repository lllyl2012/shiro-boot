package top.lllyl2012.demo2.service;

import java.util.LinkedHashMap;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfiguration {
	
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") org.apache.shiro.mgt.SecurityManager manager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(manager);
		
		bean.setLoginUrl("/login");//登录页面
		bean.setSuccessUrl("/index");//登录成功
		bean.setUnauthorizedUrl("/unauthorized");//登录成功但无权限
		//各个页面所需权限
		LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/index", "authc");
		filterChainDefinitionMap.put("/login","anon");//无需登录就能访问
		filterChainDefinitionMap.put("/loginUser","anon");
		filterChainDefinitionMap.put("/admin","roles[admin]");
		filterChainDefinitionMap.put("/edit","perms[edit]");
		filterChainDefinitionMap.put("/**", "user");//登录了就能访问
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;
		
	}
	
	@Bean("securityManager")
	public org.apache.shiro.mgt.SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(authRealm);
		return manager;
	}
	
	@Bean("authRealm")
	public AuthRealm authRealm(@Qualifier("credentialMatcher") CredentialMatcher matcher) {
		AuthRealm authRealm = new AuthRealm();
		authRealm.setCacheManager(new MemoryConstrainedCacheManager());//认证缓存
		authRealm.setCredentialsMatcher(matcher);
		return authRealm;
	}
	
	/**
	 * 比较密码
	 * @return
	 */
	@Bean("credentialMatcher")
	public CredentialMatcher credentialMatcher() {
		return new CredentialMatcher(); 
	}
	
	/**
	 * 下面两个BEAN完成shiro和spring之间的关联
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}
}
