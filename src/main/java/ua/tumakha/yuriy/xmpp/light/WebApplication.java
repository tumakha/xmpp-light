package ua.tumakha.yuriy.xmpp.light;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

/**
 * @author Yuriy Tumakha
 */
@SpringBootApplication
@EnableCaching
public class WebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    public IDialect springDataDialect() {
        return new SpringDataDialect();
    }

}
