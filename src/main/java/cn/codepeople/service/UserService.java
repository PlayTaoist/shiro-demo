/**   
 * @author lr
 * @date 2019年3月4日 上午11:49:33 
 * @version V1.0.0   
 */
package cn.codepeople.service;

import cn.codepeople.entity.User;

public interface UserService {

    public User findByName(String name);
    
    public User selectByPrimaryKey(Integer id);
}
