package com.training.springcore.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan("com.training.springcore")
@Configuration
@EnableAspectJAutoProxy
public class BigCorpApplicationConfig {

}
