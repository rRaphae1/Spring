package com.framework.spring5;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.framework.spring5"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Config {
}
