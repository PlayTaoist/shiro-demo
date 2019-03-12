/**   
 * @author lr
 * @date 2019年3月4日 上午11:36:29 
 * @version V1.0.0   
 */
package cn.codepeople.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import cn.codepeople.entity.User;
import cn.codepeople.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRealm extends AuthorizingRealm {
    
    @Autowired
    private UserService userService;
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("=====>执行授权");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User entity = userService.selectByPrimaryKey(user.getUserId());
        authorizationInfo.addStringPermission(entity.getPerms());
        
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        log.info("=====>执行认证");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        
        User user = userService.findByName(userToken.getUsername());
        
        if (user==null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }

}
