package com.training.spring.bigcorp.service.measure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.training.spring.bigcorp.service.measure", "com.training.spring.bigcorp.config.properties"})
@EnableConfigurationProperties
public class MeasureServiceConfigurationTest {
}