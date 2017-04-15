package com.timeyang.amanda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

/**
 * @author chaokunyang
 * @create 2017-04-15
 */
@EnableLoadTimeWeaving // 附件、md需要懒加载以加快速度
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
