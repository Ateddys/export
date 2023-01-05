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


#### 包结构说明
```java
|-- export
    |-- export-common
    |   |-- pom.xml
    |   |-- src
    |   |   |-- main
    |   |       |-- java
    |   |           |-- com
    |   |               |-- xiaohan
    |   |                   |-- cn
    |   |                       |-- base
    |   |                       |   |-- controller
    |   |                       |   |   |-- TBaseController.java	// 通用controller
    |   |                       |   |-- mapper
    |   |                       |   |   |-- TBaseMapper.java	// 通用mapper
    |   |                       |   |   |-- TSysConfigMapper.java	// 表配置mapper
    |   |                       |   |-- model
    |   |                       |   |   |-- BaseEntity.java	// 通用实体id
    |   |                       |   |   |-- TSysConfig.java	// 表配置表
    |   |                       |   |-- service
    |   |                       |       |-- TBaseService.java	// 通用service
    |   |                       |       |-- TBaseServiceImpl.java	// 通用service实现
    |   |                       |       |-- TSysConfigService.java	// 表配置sercice
    |   |                       |       |-- TSysConfigServiceImpl.java	// 表配置service实现
    |   |                       |-- config
    |   |                       |   |-- MybatisPlusConfig.java
    |   |                       |   |-- SwaggerPrintConfig.java
    |   |                       |-- constant
    |   |                       |   |-- BaseSymbol.java
    |   |                       |   |-- ExportContant.java
    |   |                       |-- exception
    |   |                       |   |-- BaseException.java
    |   |                       |   |-- BusinessPreconditions.java
    |   |                       |   |-- CommonExceptionHandler.java
    |   |                       |-- i18n
    |   |                       |   |-- I18nUtils.java
    |   |                       |-- result
    |   |                       |   |-- ApiResponseResult.java
    |   |                       |   |-- ApiResultCode.java
    |   |                       |   |-- MasterDataApiResultCode.java
    |   |                       |-- util
    |   |                       |   |-- DateUtils.java
    |   |                       |   |-- ListUniqUtils.java
    |   |                       |   |-- MessageUtils.java
    |   |                       |   |-- RedisUtil.java
    |   |                       |   |-- UserSessionUtil.java
    |   |                       |-- vo
    |   |                           |-- ImportProgressVo.java
    |   |                           |-- PropertyInfo.java
    |   |                           |-- UserInfo.java
    |-- export-demo
    |   |-- export-demo.iml
    |   |-- pom.xml
    |   |-- src
    |   |   |-- main
    |   |       |-- java
    |   |       |   |-- com
    |   |       |       |-- xiaohan
    |   |       |           |-- cn
    |   |       |               |-- ExportUtilDemoServiceApplication.java
    |   |       |               |-- controller
    |   |       |               |   |-- TCatController.java
    |   |       |               |-- enums
    |   |       |               |   |-- CatPubTypeEnum.java
    |   |       |               |   |-- CatSpeciesEnum.java
    |   |       |               |   |-- GenderEnum.java
    |   |       |               |-- handle
    |   |       |               |   |-- ConfigImportExcelHandler.java
    |   |       |               |-- mapper
    |   |       |               |   |-- TCatMapper.java
    |   |       |               |-- model
    |   |       |               |   |-- TCat.java
    |   |       |               |-- service
    |   |       |                   |-- TCatService.java
    |   |       |                   |-- impl
    |   |       |                       |-- TCatServiceImpl.java
    |   |       |-- resources
    |   |           |-- application.yml
    |   |           |-- banner.txt
    |   |           |-- logback-spring.xml
    |   |           |-- mapper
    |   |               |-- TCatMapper.xml
    |-- export-poi
    |   |-- export-poi.iml
    |   |-- pom.xml
    |   |-- src
    |   |   |-- main
    |   |       |-- java
    |   |           |-- com
    |   |               |-- xiaohan
    |   |                   |-- cn
    |   |                       |-- controller
    |   |                       |   |-- TBaseExcelController.java
    |   |                       |-- exporter
    |   |                       |   |-- ExportEntity.java
    |   |                       |-- handle
    |   |                       |   |-- AbstractCommonsImportExcelHandler.java
    |   |                       |-- importer
    |   |                       |   |-- AbstractImportExcelRowHandler.java
    |   |                       |   |-- ImportExcelHandler.java
    |   |                       |   |-- ImportExcelUtils.java
    |   |                       |   |-- ImportResult.java
    |   |                       |-- service
    |   |                       |   |-- ExcelService.java
    |   |                       |   |-- ExcelServiceImpl.java
    |   |                       |-- util
    |   |                           |-- ExcelFileResponse.java
    |   |                           |-- ExcelUtils.java
    |   |                           |-- ResultUtil.java

```


#### 关于我✨
🚀 [都在这里面呢！](https://1103s.cn)

#### 怎么参与贡献？

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
