# export
#### 介绍
🌈 一款为开发提供大量摸鱼时间的工具包

#### 主要特征
🌴  通用POI导入导出，只需配置表头自动解析  
💥  通用base(Controller、Service、ServiceImpl、Mapper)  
👍  统一异常处理（异常码、异常拦截）  
🎨  集成knife4j版本SwaggerUi  
🔀 集成i18n国际化  
🧱 Util包（时间、文件、缓存、入参出参VO、批数据校验重复）

#### 软件架构
无

#### 包结构说明
```java
|-- export
    |-- export-common
    |   |-- pom.xml
    |   |-- src
    |   |   |-- main
    |   |   |   |-- java
    |   |   |   |   |-- com.xiaohan.cn
    |   |   |   |       |-- base
    |   |   |   |       |   |-- controller
    |   |   |   |       |   |   |-- TBaseController.java	// 通用controller 增、删、改、查、分页
    |   |   |   |       |   |-- mapper
    |   |   |   |       |   |   |-- TBaseMapper.java	// 通用mapper
    |   |   |   |       |   |   |-- TSysConfigMapper.java	// 表配置mapper
    |   |   |   |       |   |-- model
    |   |   |   |       |   |   |-- BaseEntity.java	// 通用实体id
    |   |   |   |       |   |   |-- TSysConfig.java	// 表配置表
    |   |   |   |       |   |-- service
    |   |   |   |       |   |   |-- TBaseService.java	// 通用service
    |   |   |   |       |   |   |-- TBaseServiceImpl.java	// 通用service实现
    |   |   |   |       |   |   |-- TSysConfigService.java	// 表配置sercice
    |   |   |   |       |   |   |-- TSysConfigServiceImpl.java	// 表配置service实现
    |   |   |   |       |-- config
    |   |   |   |       |   |-- MybatisPlusConfig.java	// mybatis 分页配置、自动添加基础字段
    |   |   |   |       |   |-- SwaggerPrintConfig.java	// swagger配置
    |   |   |   |       |-- constant
    |   |   |   |       |   |-- BaseSymbol.java	// 系统常量
    |   |   |   |       |   |-- ExportContant.java	// 导出常量
    |   |   |   |       |-- exception
    |   |   |   |       |   |-- BaseException.java	// 自定义基础异常类
    |   |   |   |       |   |-- CommonExceptionHandler.java	// 全局异常拦截
    |   |   |   |       |-- i18n
    |   |   |   |       |   |-- I18nUtils.java	// 国际化信息工具，根据key获取国际化文本
    |   |   |   |       |-- result
    |   |   |   |       |   |-- ApiResponseResult.java	// 接口返回结果封装类
    |   |   |   |       |   |-- ApiResultCode.java	// 全局结果码, 表示接口处理结果状态
    |   |   |   |       |   |-- ExcelDataApiResultCode.java	// excel导出结果码, 表示接口处理结果状态
    |   |   |   |       |-- util
    |   |   |   |       |   |-- DateUtils.java	// 时间工具类
    |   |   |   |       |   |-- ListUniqUtils.java	// 校验导入excel数据时 当前批重复的情况
    |   |   |   |       |   |-- MessageUtils.java	// 含有消息国际化的异常构建工具类
    |   |   |   |       |   |-- RedisUtil.java	// redis工具类
    |   |   |   |       |   |-- UserSessionUtil.java	// session用户信息
    |   |   |   |       |-- vo
    |   |   |   |           |-- ImportProgressVo.java   // 导入进度状态
    |   |   |   |           |-- PropertyInfo.java   // 属性映射
    |   |   |   |           |-- UserInfo.java   // 用户缓存信息
    |-- export-poi
    |   |-- export-poi.iml
    |   |-- pom.xml
    |   |-- src
    |   |   |-- main
    |   |   |    |-- java
    |   |   |    |   |-- com.xiaohan.cn
    |   |   |    |       |-- controller
    |   |   |    |       |   |-- TBaseExcelController.java	// 通用controller 含有导入、导出、异步导入结果
    |   |   |    |       |-- exporter
    |   |   |    |       |   |-- ExportEntity.java	// ExportEntity 导出实体
    |   |   |    |       |-- handle
    |   |   |    |       |   |-- AbstractCommonsImportExcelHandler.java	// 导入导出抽象类
    |   |   |    |       |-- importer
    |   |   |    |       |   |-- AbstractImportExcelRowHandler.java	// Excel导入行处理抽象类
    |   |   |    |       |   |-- ImportExcelHandler.java	// Excel导入处理接口
    |   |   |    |       |   |-- ImportExcelUtils.java	// 导入Excel文件工具类
    |   |   |    |       |   |-- ImportResult.java	// 导入Excel结果包装类
    |   |   |    |       |-- service
    |   |   |    |       |   |-- ExcelService.java	// 导入导出接口
    |   |   |    |       |   |-- ExcelServiceImpl.java	// 导入导出实现
    |   |   |    |       |-- util
    |   |   |    |           |-- ExcelFileResponse.java	// excel 兼容mac浏览器
    |   |   |    |           |-- ExcelUtils.java	// Excel工具类
    |   |   |    |           |-- ResultUtil.java	// 结果处理工具类
    |-- export-demo
    |   |-- export-demo.iml
    |   |-- pom.xml
    |   |-- src
    |   |   |-- main
    |   |   |   |-- java
    |   |   |   |   |-- com.xiaohan.cn
    |   |   |   |       |-- ExportUtilDemoServiceApplication.java
    |   |   |   |       |-- controller
    |   |   |   |       |   |-- TCatController.java
    |   |   |   |       |-- enums
    |   |   |   |       |   |-- CatPubTypeEnum.java
    |   |   |   |       |   |-- CatSpeciesEnum.java
    |   |   |   |       |   |-- GenderEnum.java
    |   |   |   |       |-- handle
    |   |   |   |       |   |-- ConfigImportExcelHandler.java
    |   |   |   |       |-- mapper
    |   |   |   |       |   |-- TCatMapper.java
    |   |   |   |       |-- model
    |   |   |   |       |   |-- TCat.java
    |   |   |   |       |-- service
    |   |   |   |           |-- TCatService.java
    |   |   |   |           |-- impl
    |   |   |   |               |-- TCatServiceImpl.java
    |   |   |   |-- resources
    |   |   |       |-- application.yml
    |   |   |       |-- banner.txt
    |   |   |       |-- logback-spring.xml
    |   |   |       |-- mapper
    |   |   |           |-- TCatMapper.xml
```

#### 关于我✨
🚀 [都在这里面呢！](https://1103s.cn)

#### 怎么参与贡献？

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
