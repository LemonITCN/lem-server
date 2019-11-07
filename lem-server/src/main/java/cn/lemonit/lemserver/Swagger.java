package cn.lemonit.lemserver;//package com.zxy.first;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * Swagger2启动类
// * Created by Arvin on 2017/12/7.
// */
//@Configuration
//@EnableSwagger2
//public class Swagger {
//
//    /**
//     * 创建用户API文档
//     * @return
//     */
//    @Bean
//    public Docket createRestUserApi(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                   .groupName("user")
//                .apiInfo(createUserApiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.zxy.first.controller"))
//                .paths(PathSelectors.any())
//                .build();
//
//    }
//
////    /**
////     * 创建客户API文档
////     * @RETURN
////     */
////    @BEAN
////    PUBLIC DOCKET CREATERESTCUSAPI(){
////        RETURN NEW DOCKET(DOCUMENTATIONTYPE.SWAGGER_2)
////                .GROUPNAME("CUS")
////                .APIINFO(CREATECUSAPIINFO())
////                .SELECT()
////                .APIS(REQUESTHANDLERSELECTORS.BASEPACKAGE("COM.LHR.OLDMAN.CONTROLLER"))
////                .PATHS(PATHSELECTORS.ANY())
////                .BUILD();
////    }
//
//    /**
//     * 创建用户API信息
//     * @return
//     */
//    private ApiInfo createUserApiInfo(){
//        return new ApiInfoBuilder()
//                .title("处理用户信息相关接口")
//                .description("以下接口只操作用户信息")
//                .contact(new Contact("Arvin","http://blog.csdn.net/tenghu8888","libiaohu@126.com"))
//                .version("1.0")
//                .build();
//    }
//
//    /**
//     * 创建客户API信息
//     * @return
//     */
//    private ApiInfo createCusApiInfo(){
//        return new ApiInfoBuilder()
//                .title("处理客户信息相关接口")
//                .description("以下接口只操作客户信息")
//                .contact(new Contact("Arvin","http://blog.csdn.net/tenghu8888","libiaohu@126.com"))
//                .version("1.0")
//                .build();
//    }
//}
//
