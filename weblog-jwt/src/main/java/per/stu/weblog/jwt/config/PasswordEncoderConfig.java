package per.stu.weblog.jwt.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;

/**
 * PasswordEncoderConfig
 * PasswordEncoder 接口是 Spring Security 提供的密码加密接口，它定义了密码加密和密码验证的方法。通过实现这个接口，您可以将密码加密为不可逆的哈希值，以及在验证密码时对比哈希值。
 *
 * @author Stu
 * @date 2022/01/26 16:52
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt 是一种安全且适合密码存储的哈希算法，它在进行哈希时会自动加入“盐”，增加密码的安全性。
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}
