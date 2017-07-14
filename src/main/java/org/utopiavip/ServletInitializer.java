package org.utopiavip;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * 注意: ServletInitializer类名不要改
 * SpringBoot 打war包部署参考: http://www.cnblogs.com/tomlxq/p/5511954.html
 */
@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AcceleratorApplication.class);
    }

}
