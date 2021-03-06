/**   
 * @author lr
 * @date 2019年3月4日 上午11:33:50 
 * @version V1.0.0   
 */
package cn.codepeople.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    /**
     * Shiro内置过滤器，可以实现权限相关的拦截器
     *      常用的过滤器：
     *          anon: 无需认证(登录)可以访问
     *          authc: 必须认证才可以访问
     *          user: 如果使用rememberMe的功能可以直接访问
     *          perms: 该资源必须得到资源权限才可以访问
     *          role: 该资源必须得到角色权限才可以访问
     */
    @Bean
    public ShiroFilterFactoryBean  getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean  shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        
        Map<String, String> filterMap= new LinkedHashMap<String, String>();
        filterMap.put("/login", "anon");
        filterMap.put("/add", "perms[sys:user:add]");
        filterMap.put("/update", "perms[sys:user:update]");
        filterMap.put("/*", "authc");
        
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");
        return shiroFilterFactoryBean;
    }
    
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    
    @Bean(name="userRealm")
    public UserRealm getUserRealm() {
        return new UserRealm();
    }
    
}
