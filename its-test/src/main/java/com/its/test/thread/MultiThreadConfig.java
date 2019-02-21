package com.its.test.thread;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = { "com.its.test.thread" })
@ImportResource(value = { "classpath:applicationContext.xml" })
@EnableScheduling
public class MultiThreadConfig {
}
