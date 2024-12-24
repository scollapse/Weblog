package per.stu.weblog.jwt.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import per.stu.weblog.common.enums.ResponseCodeEnum;
import per.stu.weblog.common.utils.Response;
import per.stu.weblog.jwt.utils.ResultUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 用户未登录时返回的错误信息
 * @author: syl
 * @create: 2024-12-24 09:46
 **/
@Component
@Slf4j
public class RestAuthenticationEntryPoint  implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("用户未登录，无法访问系统资源，请先登录！");
        if(authException instanceof InsufficientAuthenticationException){
            ResultUtil.fail(response, HttpStatus.UNAUTHORIZED.value(), Response.fail(ResponseCodeEnum.UNAUTHORIZED));
            return;
        }
        ResultUtil.fail(response, HttpStatus.UNAUTHORIZED.value(), Response.fail(authException.getMessage()));
    }
}
