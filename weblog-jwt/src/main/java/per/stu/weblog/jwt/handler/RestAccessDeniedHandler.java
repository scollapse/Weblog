package per.stu.weblog.jwt.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import per.stu.weblog.common.enums.ResponseCodeEnum;
import per.stu.weblog.common.utils.Response;
import per.stu.weblog.jwt.utils.ResultUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 登录后无权限访问接口时，返回的错误信息
 * @author: syl
 * @create: 2024-12-24 09:53
 **/
@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("无权限访问接口：{}", request.getRequestURI());
        // 预留接口，后续可根据实际情况返回不同错误信息
        ResultUtil.fail(response, Response.fail(ResponseCodeEnum.ACCESS_DENIED));
    }

}
