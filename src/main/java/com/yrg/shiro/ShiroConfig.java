package com.yrg.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactorBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //定义安全管理器
        factoryBean.setSecurityManager(securityManager);

        //添加Shiro内置过滤器
        /**
         * Shiro内置的过滤器，可以实现权限相关的拦截器
         *    常用的过滤器：
         *       anon：无需认证（登陆）可以访问
         *       authc：必须认证才可以访问
         *       user：如果使用rememberMe的功能可以直接访问
         *       perms：该资源必须得到资源权限才可以访问
         *       role：该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterMap=new LinkedHashMap<>();
        /*filterMap.put("/add","authc");
        filterMap.put("/update","authc");*/
        //放行
        filterMap.put("/testThymeleaf","anon");
        filterMap.put("/login","anon");

        //授权过滤器
        //当前授权拦截后，shrio会自动跳转到未授权页面
        filterMap.put("/*","perms[user:all]");
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/update","perms[user:update]");

        //拦截
        filterMap.put("/*","authc");

        //设置登陆页面
        factoryBean.setLoginUrl("/toLogin");

        //设置未授权提示页面
        factoryBean.setUnauthorizedUrl("/unAuth");

        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("getRealm")UserRealm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联Realm
        securityManager.setRealm(realm);
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean
    public UserRealm getRealm(){
        return new UserRealm();
    }

    /**
     * 配置ShiroDialect,用于Shiro和thymeleaf标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
