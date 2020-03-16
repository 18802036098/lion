package com.lion.demo.consumer;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * CodeGenerator
 * MybatisPlus 代码生成类
 *
 * @author Yanzheng https://github.com/micyo202
 * @date 2020/2/12
 * Copyright 2020 Yanzheng. All rights reserved.
 */
public class CodeGenerator {

    private static final String AUTHOR = "Yanzheng https://github.com/micyo202";
    private static final String URL = "jdbc:mysql://localhost:3306/lion?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "lion";
    private static final String PASSWORD = "lion";
    private static final String PACKAGE = "com.lion.demo.consumer";         // 根据实际项目包路径修改
    private static final String PROJECT = "lion-demo/lion-demo-consumer";   // 根据实际项目根路径修改

    public static void main(String[] args) {

        String rootDir = System.getProperty("user.dir");
        String outPath = String.format("%s/%s", rootDir, PROJECT);

        AutoGenerator autoGenerator = new AutoGenerator();
        /**
         * 全局配置
         */
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor(AUTHOR);
        globalConfig.setOutputDir(outPath + "/src/main/java");
        globalConfig.setFileOverride(true);     // 是否覆盖同名文件（默认false）
        globalConfig.setActiveRecord(true);
        //globalConfig.setSwagger2(true);
        globalConfig.setEnableCache(false);      // XML 二级缓存
        globalConfig.setBaseResultMap(true);    // XML ResultMap
        globalConfig.setBaseColumnList(true);   // XML ColumList
        globalConfig.setOpen(false);
        autoGenerator.setGlobalConfig(globalConfig);

        /**
         * 数据源配置
         */
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(URL);
        dataSourceConfig.setDriverName(DRIVER_NAME);
        dataSourceConfig.setUsername(USERNAME);
        dataSourceConfig.setPassword(PASSWORD);
        /*
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert(){
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注：processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });
        */
        autoGenerator.setDataSource(dataSourceConfig);

        /**
         * 包配置
         */
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(scanner("模块名"));
        packageConfig.setParent(PACKAGE);
        autoGenerator.setPackageInfo(packageConfig);

        /**
         * 策略配置
         */
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        // 公共父类
        strategyConfig.setSuperControllerClass("com.lion.common.base.controller.BaseController");
        strategyConfig.setSuperServiceClass("com.lion.common.base.service.IBaseService");
        strategyConfig.setSuperServiceImplClass("com.lion.common.base.service.impl.BaseServiceImpl");
        //strategyConfig.setSuperMapperClass("父类,没有就不用设置");
        //strategyConfig.setSuperEntityClass("父类,没有就不用设置");
        //strategyConfig.setSuperEntityColumns("父类公共字段,没有就不用设置");
        strategyConfig.setControllerMappingHyphenStyle(true);
        //strategyConfig.setTablePrefix(packageConfig.getModuleName() + "_");
        strategyConfig.setInclude(scanner("表名（多个用英文逗号分割）").split(","));
        autoGenerator.setStrategy(strategyConfig);

        /**
         * 注入配置
         * 可以在 VM 中使用 cfg.xxx
         */
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("xxx", this.getConfig().getGlobalConfig().getAuthor() + "-rb");
                this.setMap(map);
            }
        };

        // 调整 xml 生成目录
        ArrayList<FileOutConfig> fileOutConfigs = new ArrayList<>();
        fileOutConfigs.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(outPath)
                        .append("/src/main/resources/mapper/")
                        .append(packageConfig.getModuleName())
                        .append("/")
                        .append(tableInfo.getEntityName())
                        .append("Mapper.xml");
                return stringBuilder.toString();
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigs);
        autoGenerator.setCfg(injectionConfig);

        // 关闭默认 xml 生成，调整生成至根目录
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);

        autoGenerator.execute();
    }

    /**
     * 获取输入
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("请输入" + tip + "：");
        System.out.println(stringBuilder.toString());
        if (scanner.hasNext()) {
            String next = scanner.next();
            if (StringUtils.isNotEmpty(next)) {
                return next;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
