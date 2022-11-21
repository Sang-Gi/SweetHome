package com.ssafy.sweethome.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = { "com.ssafy.**.mapper" })
public class DatabaseConfiguration {}