package per.stu.weblog.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Username or password is null exception
 * @author Stu
 * @date 2021/07/16
 * @see org.springframework.security.authentication.BadCredentialsException
 */

public class UsernameOrPasswordNullException extends AuthenticationException {

    public UsernameOrPasswordNullException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UsernameOrPasswordNullException(String msg) {
        super(msg);
    }
}
