package per.stu.weblog.admin.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.stu.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import per.stu.weblog.admin.service.AdminUserService;
import per.stu.weblog.common.aspect.ApiOperationLog;
import per.stu.weblog.common.utils.Response;

/**
 * @description: 用户管理
 * @author: syl
 * @create: 2024-12-26 17:59
 **/
@RestController
@RequestMapping("/admin/user")
@Api(tags = "后台用户管理")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/password/update")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiOperationLog(description = "修改密码")
    public Response updatePassword(@RequestBody @Validated UpdateAdminUserPasswordReqVO reqVO) {
        return adminUserService.updatePassword(reqVO);
    }

    @PostMapping("/info/findCurrent")
    @ApiOperation(value = "查询当前用户信息", notes = "查询当前用户信息")
    @ApiOperationLog(description = "查询当前用户信息")
    public Response findCurrentUserInfo(){
        return adminUserService.findCurrentUserInfo();
    }
}
