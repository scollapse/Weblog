package per.stu.weblog.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import per.stu.weblog.common.aspect.ApiOperationLog;
import per.stu.weblog.common.enums.ResponseCodeEnum;
import per.stu.weblog.common.excption.BizException;
import per.stu.weblog.common.utils.Response;
import per.stu.weblog.web.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
@Api(tags = "测试接口")
public class TestController {



    /**
     * create by: syl
     * description: 测试接口
     * create time: 2024/12/17 14:56
     * @param user
     * @return @return per.stu.weblog.web.model.User
     *
     */
    @PostMapping("/admin/test")
    @ApiOperationLog(description = "测试接口")
    @ApiOperation(value = "测试接口", notes = "测试接口")
    public Response test(@RequestBody @Validated User user) {
        // 返参
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateDate(LocalDate.now());
        user.setTime(LocalTime.now());
        return Response.success(user);
    }

    /**
     * create by: syl
     * description: 测试异常接口
     * create time: 2024/12/17 14:56
     * @param user
     * @return
     */
    @PostMapping("/testBizException")
    @ApiOperationLog(description = "测试业务异常接口")
    @ApiOperation(value = "测试业务异常接口", notes = "测试业务异常接口")
    public Response testBizException(@RequestBody @Validated User user ) {
       // 手动抛出异常
        throw new BizException(ResponseCodeEnum.PRODUCT_NOT_FOUND);
    }

    @PostMapping("/testGlobalException")
    @ApiOperationLog(description = "测试全局异常接口")
    @ApiOperation(value = "测试全局异常接口", notes = "测试全局异常接口")
    public Response testGlobalException(@RequestBody @Validated User user ) {
        int i = 1 / 0;
        return Response.success();
    }

    @PostMapping("/admin/update")
    @ApiOperationLog(description = "测试更新接口")
    @ApiOperation(value = "测试更新接口")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response testUpdate() {
        log.info("更新成功...");
        return Response.success();
    }


}
