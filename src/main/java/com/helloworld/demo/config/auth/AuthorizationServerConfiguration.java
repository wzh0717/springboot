//package com.helloworld.demo.config.auth;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//
//import javax.sql.DataSource;
//
///**
// * 配置OAuth2验证服务器
// *
// * @author wangzh 2019/02/17 21:26
// */
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {
//
//    private Logger logger = LoggerFactory.getLogger(AuthorizationServerConfiguration.class);
//
//    private static final String ENV_OAUTH = "authentication.oauth.";
//    private static final String PROP_CLIENTID = "clientid";
//    private static final String PROP_SECRET = "secret";
//    private static final String PROP_TOKEN_VALIDITY_SECONDS = "tokenValidityInSeconds";
//
//    private RelaxedPropertyResolver propertyResolver;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }
//
//    /**
//     * 认证方式
//     */
//    @Autowired
//    @Qualifier("authenticationManagerBean")
//    private AuthenticationManager authenticationManager;
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(tokenStore())
//                .authenticationManager(authenticationManager);
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        // 使用in-memory存储
//        clients.inMemory()
//                ////client_id用来标识客户的Id
//                .withClient(propertyResolver.getProperty(PROP_CLIENTID))
//                //允许授权范围
//                .scopes("read", "write")
//                //客户端可以使用的权限
//                .authorities("ROLE_ADMIN", "ROLE_USER")
//                //允许授权类型
//                .authorizedGrantTypes("password", "refresh_token")
//                //secret客户端安全码
//                .secret(propertyResolver.getProperty(PROP_SECRET))
//                .accessTokenValiditySeconds(propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 1800));
//
//    }
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
//    }
//}
