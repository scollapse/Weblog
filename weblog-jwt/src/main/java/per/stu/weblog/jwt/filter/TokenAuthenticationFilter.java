package per.stu.weblog.jwt.filter;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import per.stu.weblog.jwt.utils.JwtTokenHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @description: TokenAuthenticationFilter
 * @author: syl
 * @create: 2024-12-24 09:29
 **/
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.tokenHeaderKey}")
    private String tokenHeaderKey;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        //先判断请求是否是以/admin开头，如果是则检查 token
        String uri = request.getRequestURI();
        if (uri.startsWith("/admin")) {

            // 从请求头中获取token
            String header = request.getHeader(tokenHeaderKey);

            // 判断 vlaue 中是否以 Bearer 开头
            if (null != header && header.startsWith(tokenPrefix)) {
                String token = header.substring(7);
                log.info("token:{}", token);
                // 判空
                if (StringUtils.isNoneBlank(token)) {
                    try {
                        // 校验 token 是否可用
                        jwtTokenHelper.validateToken(token);
                    } catch (SignatureException | MalformedJwtException | UnsupportedJwtException |
                             IllegalArgumentException e) {
                        // token 校验失败
                        authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 不可用"));
                        return;
                    } catch (ExpiredJwtException e) {
                        // token 过期，返回 401 状态码
                        authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 过期"));
                        return;
                    }
                    // token 校验成功，获取用户信息
                    String username = jwtTokenHelper.getUsernameFromToken(token);
                    log.info("username:{}", username);
                    if (StringUtils.isNoneBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                        // 根据用户名获取用户详情
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        // 保存用户信息到 SecurityContextHolder
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // 放入安全上下文
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        // 继续执行下一个过滤器
        chain.doFilter(request, response);
    }
}
