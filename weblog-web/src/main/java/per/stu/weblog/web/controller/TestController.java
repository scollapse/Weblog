package per.stu.weblog.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import per.stu.weblog.common.aspect.ApiOperationLog;
import per.stu.weblog.common.utils.Response;
import per.stu.weblog.web.model.User;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

/**
 * 测试控制器
 *
 * @author babax
 * @version v1.0
 * @date 2024/12/13 10:50
 * @modified by
 */
@RestController
@Slf4j
public class TestController {



    /**
     * create by: syl
     * description: 测试接口
     * create time: 2024/12/17 14:56
     * @param user
     * @param bindingResult 验证的结果对象，其中包含所有验证错误信息
     * @return @return per.stu.weblog.web.model.User
     *
     */
    @PostMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public Response test(@RequestBody @Validated User user, BindingResult bindingResult) {
        // 是否存在校验错误
        if (bindingResult.hasErrors()) {
            // 获取校验不通过字段的提示信息
            String errorMsg = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            return Response.fail(errorMsg);
        }
        // 返参
        return Response.success(user);
    }

}
