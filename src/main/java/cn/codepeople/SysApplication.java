/**   
 * @author lr
 * @date 2019年3月4日 上午11:30:02 
 * @version V1.0.0   
 */
package cn.codepeople;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.codepeople.mapper.**")
public class SysApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysApplication.class, args);
    }
}
