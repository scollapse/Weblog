package per.stu.weblog.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import per.stu.weblog.common.aspect.ApiOperationLog;
import per.stu.weblog.web.model.User;

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

    @PostMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public User test(@RequestBody User user) {
        return user;
    }
}
