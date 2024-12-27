package per.stu.weblog.admin.service;

import per.stu.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import per.stu.weblog.common.utils.Response;

/**
 * @author Stu
 * @date 2021/07/16 16:22
 *
 */
public interface AdminUserService {

    /**
     * 更新管理员用户密码
     * @param reqVO
     * @return
     */
    Response updatePassword(UpdateAdminUserPasswordReqVO reqVO);

    /**
     * 查询管理员用户信息
     * @param
     * @return
     */
    Response findCurrentUserInfo();
}
