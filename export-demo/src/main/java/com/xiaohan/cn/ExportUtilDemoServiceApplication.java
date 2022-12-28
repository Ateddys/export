package com.xiaohan.cn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "com.xiaohan.cn")
@ServletComponentScan
@Slf4j
public class ExportUtilDemoServiceApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ExportUtilDemoServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //    legendsCodeGenerator.run();
    }
}
