package per.stu.weblog.jwt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import per.stu.weblog.common.domain.dos.UserDO;
import per.stu.weblog.common.domain.dos.UserRoleDO;
import per.stu.weblog.common.domain.mapper.UserMapper;
import per.stu.weblog.common.domain.mapper.UserRoleMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中查询
        UserDO userDO = userMapper.findByUsername(username);

        // 判断用户是否存在
        if (Objects.isNull(userDO)) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        // 获取用户角色
        List<UserRoleDO> userRoleDOS = userRoleMapper.selectByUsername(username);
        String[] roles = null;
        // 转数组
        if (!CollectionUtils.isEmpty(userRoleDOS)) {
            roles =  userRoleDOS.stream().map(UserRoleDO::getRole).collect(Collectors.toList()).toArray(new String[userRoleDOS.size()]);
        }

        // authorities 用于指定角色，这里写死为 ADMIN 管理员
        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities(roles)
                .build();
    }
}
