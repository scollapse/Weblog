package per.stu.weblog.admin.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import per.stu.weblog.jwt.config.JwtAuthenticationSecurityConfig;
import per.stu.weblog.jwt.filter.TokenAuthenticationFilter;
import per.stu.weblog.jwt.handler.RestAccessDeniedHandler;
import per.stu.weblog.jwt.handler.RestAuthenticationEntryPoint;

/**
 * @author Stu
 * @date 2021/07/16
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(). // 禁用 csrf
                formLogin().disable() // 禁用表单登录
                .apply(jwtAuthenticationSecurityConfig) // 设置用户登录认证相关配置
                .and()
                .authorizeRequests()
                .mvcMatchers("/admin/**").authenticated() // 认证所有以 /admin 为前缀的 URL 资源
                .anyRequest().permitAll() // 其他都需要放行，无需认证
                .and()
                .httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint) // 处理未登录的用户请求
                .and()
                .exceptionHandling().accessDeniedHandler(restAccessDeniedHandler) // 处理无权限访问的用户请求
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 前后端分离，无需创建会话
                .and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    /**
     * Token 校验过滤器
     * @return
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
}
