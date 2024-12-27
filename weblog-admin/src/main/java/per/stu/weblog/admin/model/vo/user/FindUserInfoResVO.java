package per.stu.weblog.admin.model.vo.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 查询用户信息返回值对象
 * @author: syl
 * @create: 2024-12-27 09:56
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUserInfoResVO {
    /**
     * 用户名
     */
    private String username;
}
