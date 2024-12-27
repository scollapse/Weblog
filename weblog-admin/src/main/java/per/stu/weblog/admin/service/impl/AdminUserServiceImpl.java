package per.stu.weblog.admin.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import per.stu.weblog.admin.model.vo.user.FindUserInfoResVO;
import per.stu.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import per.stu.weblog.admin.service.AdminUserService;
import per.stu.weblog.common.domain.mapper.UserMapper;
import per.stu.weblog.common.enums.ResponseCodeEnum;
import per.stu.weblog.common.utils.Response;

/**
 * @description:  管理员用户服务实现类
 * @author: syl
 * @create: 2024-12-26 17:52
 **/
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 更新管理员用户密码
     * @param reqVO
     * @return
     * @throws Exception
     */
    @Override
    public Response updatePassword(UpdateAdminUserPasswordReqVO reqVO) {
        // 拿到用户名和密码
        String username = reqVO.getUsername();
        String password = reqVO.getPassword();
        // 加密 密码
        String encryptedPassword = passwordEncoder.encode(password);
        // 更新密码
        int count = userMapper.updatePassword(username, encryptedPassword);
        return count == 1? Response.success() : Response.fail(ResponseCodeEnum.USER_NOT_FOUND);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @Override
    public Response findCurrentUserInfo() {
        //获取存储在 ThreadLocal 中的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取当前登录用户的用户名
        String username = authentication.getName();
        return Response.success(FindUserInfoResVO.builder().username(username).build());
    }
}
