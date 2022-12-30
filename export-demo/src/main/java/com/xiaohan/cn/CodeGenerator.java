package com.xiaohan.cn;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/***
 * mybatisplus代码生成器
 * @author by teddy
 * @date 2022/12/29 11:06
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 获取项目路径
        String projectPath = System.getProperty("user.dir");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        mpg.setGlobalConfig(setGlobalConfig(projectPath));

        // 数据源配置
        mpg.setDataSource(setDataSourceConfig());

        // 包配置
        mpg.setPackageInfo(setPackageConfig());

        // 自定义配置
        mpg.setCfg(setCfg(projectPath));

        // 配置模板
//        mpg.setTemplate(setTemplateConfig());

        // 策略配置
        mpg.setStrategy(setStragy());

        mpg.execute();
    }

    /**
     * 策略配置
     * @return 策略配置
     */
    private static StrategyConfig setStragy() {
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);
        sc.setRestControllerStyle(true);
        sc.setControllerMappingHyphenStyle(true);
        sc.setLogicDeleteFieldName("deleted");
        sc.setInclude(scanner("表名，多个英文逗号分割").split(","));
        //设置自动填充配置
        ArrayList<TableFill> tableFills=new ArrayList<>();
        tableFills.add(new TableFill("create_time", FieldFill.INSERT));
        tableFills.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        sc.setTableFillList(tableFills);
        // 乐观锁
        sc.setVersionFieldName("version");
        // 驼峰命名
        sc.setRestControllerStyle(true);
        return sc;
    }

    /**
     * 自定义配置
     * @param projectPath 主路径
     * @return 自定义配置
     */
    private static InjectionConfig setCfg(String projectPath) {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }

        });
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir(filePath);
                return false;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 模板配置
     *
     * @return 模板配置
     */
    private static TemplateConfig setTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/entity.java");
        templateConfig.setController("templates/controller.java");
        templateConfig.setService("templates/service.java");
        templateConfig.setServiceImpl("templates/serviceImpl.java");
        templateConfig.setMapper("templates/mapper.java");
        templateConfig.setXml("templates/mapper.xml");
        return templateConfig;
    }

    /**
     * 包配置
     *
     * @return 包配置
     */
    private static PackageConfig setPackageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.xiaohan.cn");
        pc.setEntity("model");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        // pc.setXml("mapper.xml");
        return pc;
    }

    /**
     * 数据源配置
     *
     * @return 数据源配置
     */
    private static DataSourceConfig setDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/life?characterEncoding=utf8&useUnicode=true&allowPublicKeyRetrieval=true&useSSL=false");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        return dsc;
    }


    /**
     * 全局配置
     * @return 全局配置
     */
    private static GlobalConfig setGlobalConfig(String projectPath) {
        GlobalConfig gc = new GlobalConfig();
        // gc.setOutputDir(String.format("%s/%s/src/main/ava" , projectPath, scanner("模块名")));
        gc.setOutputDir(String.format("%s/export-demo/src/main/java", projectPath));
        //是否覆盖以前文件
        gc.setFileOverride(true);
        //是否打开生成目录
        gc.setOpen(false);
        //设置项目作者名称
        gc.setAuthor("teddy");
        //设置主键策略
        gc.setIdType(IdType.AUTO);
        //生成基本ResultMap
        gc.setBaseResultMap(true);
        //生成基本ColumnList
        gc.setBaseColumnList(true);
        //去掉服务默认前缀
        gc.setServiceName("%sService");
        //设置时间类型
        gc.setDateType(DateType.ONLY_DATE);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        return gc;
    }

    /**
     * 输入scanner
     *
     * @param tip 值
     * @return 输入值
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}


