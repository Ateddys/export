package com.xiaohan.cn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.legendscloud.db.generator.LegendsCodeGenerator;


@MapperScan("com.xiaohan.cn.**.mapper")
@SpringBootApplication(scanBasePackages = "com.xiaohan.cn")
public class ExportUtilDemoServiceApplication implements ApplicationRunner {

    @Autowired
    LegendsCodeGenerator legendsCodeGenerator;

    public static void main(String[] args) {
        SpringApplication.run(ExportUtilDemoServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        legendsCodeGenerator.run();
    }

}
