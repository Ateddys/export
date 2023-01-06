# Export
#### Introduce
🌈 A toolkit that provides a lot of fishing time for development

#### Main features
🌴   General POI import and export, only need to configure the table header to automatically parse  
💥   current base(Controller、Service、ServiceImpl、Mapper)  
👍   Unified exception handling (exception code, exception interception)  
🎨   Integrate knife4j version Swagger Ui  
🔀   Integrated i18n internationalization  
🧱   Util package (time, file, cache, input and output parameters VO, batch data verification repetition)

#### Software architecture
none

#### Description of package structure
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
    |   |   |   |       |   |   |-- TBaseController.java	// General controller add, delete, modify, check, paging
    |   |   |   |       |   |-- mapper
    |   |   |   |       |   |   |-- TBaseMapper.java	// General mapper
    |   |   |   |       |   |   |-- TSysConfigMapper.java	// Table configuration mapper
    |   |   |   |       |   |-- model
    |   |   |   |       |   |   |-- BaseEntity.java	// Generic entity id
    |   |   |   |       |   |   |-- TSysConfig.java	// Table configuration table
    |   |   |   |       |   |-- service
    |   |   |   |       |   |   |-- TBaseService.java	// General service
    |   |   |   |       |   |   |-- TBaseServiceImpl.java	// General service implementation
    |   |   |   |       |   |   |-- TSysConfigService.java	// Table configuration service
    |   |   |   |       |   |   |-- TSysConfigServiceImpl.java	// Table configuration service implementation
    |   |   |   |       |-- config
    |   |   |   |       |   |-- MybatisPlusConfig.java	// Mybatis config (Pagination configuration, automatic addition of basic fields)
    |   |   |   |       |   |-- SwaggerPrintConfig.java	// Swagger configuration
    |   |   |   |       |-- constant
    |   |   |   |       |   |-- BaseSymbol.java	// System constant
    |   |   |   |       |   |-- ExportContant.java	// Export constant
    |   |   |   |       |-- exception
    |   |   |   |       |   |-- BaseException.java	// Custom base exception class
    |   |   |   |       |   |-- CommonExceptionHandler.java	// Global exception interception
    |   |   |   |       |-- i18n
    |   |   |   |       |   |-- I18nUtils.java	// Internationalized information tool, get internationalized text according to key
    |   |   |   |       |-- result
    |   |   |   |       |   |-- ApiResponseResult.java	// The interface returns the result encapsulation class
    |   |   |   |       |   |-- ApiResultCode.java	// Global result code, indicating the status of the interface processing result
    |   |   |   |       |   |-- ExcelDataApiResultCode.java	// Excel export result code, indicating the status of the interface processing result
    |   |   |   |       |-- util
    |   |   |   |       |   |-- DateUtils.java	// Time tools class
    |   |   |   |       |   |-- ListUniqUtils.java	// Verify that the current batch is repeated when importing excel data
    |   |   |   |       |   |-- MessageUtils.java	// Exception build tool class with message internationalization
    |   |   |   |       |   |-- RedisUtil.java	// Tedis tool class
    |   |   |   |       |   |-- UserSessionUtil.java	// Session user information
    |   |   |   |       |-- vo
    |   |   |   |           |-- ImportProgressVo.java	// Import progress status
    |   |   |   |           |-- PropertyInfo.java   // Attribute mapping
    |   |   |   |           |-- UserInfo.java   // User Info
    |-- export-poi
    |   |-- export-poi.iml
    |   |-- pom.xml
    |   |-- src
    |   |   |-- main
    |   |   |    |-- java
    |   |   |    |   |-- com.xiaohan.cn
    |   |   |    |       |-- controller
    |   |   |    |       |   |-- TBaseExcelController.java	// General controller contains import, export, asynchronous import results
    |   |   |    |       |-- exporter
    |   |   |    |       |   |-- ExportEntity.java	// ExportEntity export entities
    |   |   |    |       |-- handle
    |   |   |    |       |   |-- AbstractCommonsImportExcelHandler.java	// Import and export abstract classes
    |   |   |    |       |-- importer
    |   |   |    |       |   |-- AbstractImportExcelRowHandler.java	// Excel import row processing abstract class
    |   |   |    |       |   |-- ImportExcelHandler.java	// Excel import processing interface
    |   |   |    |       |   |-- ImportExcelUtils.java	// Import Excel file tool class
    |   |   |    |       |   |-- ImportResult.java	// Import Excel result wrapper class
    |   |   |    |       |-- service
    |   |   |    |       |   |-- ExcelService.java	// Import and export interface
    |   |   |    |       |   |-- ExcelServiceImpl.java	// Import export implementation
    |   |   |    |       |-- util
    |   |   |    |           |-- ExcelFileResponse.java	// Excel compatible with mac browsers
    |   |   |    |           |-- ExcelUtils.java	// Excel tools
    |   |   |    |           |-- ResultUtil.java	// Result processing tool class
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

#### About me ✨
🚀 [it's all in here！](https://1103s.cn)

#### How to contribute？

1.  Fork this warehouse
2.  New Feat_xxx branch
3.  Submit code
4.  New pull Request
