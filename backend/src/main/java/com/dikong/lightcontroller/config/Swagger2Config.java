package com.dikong.lightcontroller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月02日下午6:55
 * @see
 *      </P>
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Autowired
    private Environment environment;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.dikong.lightcontroller.controller"))
                .paths(PathSelectors.any()).build().globalOperationParameters(setHeaderToken());
    }

    private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("token").modelRef(new ModelRef("string"))
                .parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        return pars;
    }


    private ApiInfo apiInfo() {
        String host = environment.getProperty("server.address", "127.0.0.1");
        String port = environment.getProperty("server.port", "9494");
        String url = "http://" + host + ":" + port;
        return new ApiInfoBuilder().title("路灯控制系统").description("路灯控制设备.时序.群组.监控等功能")
                .contact(new Contact("冷荣富", url, "rongfu.leng@pohoocredit.com")).version("1.0")
                .build();
    }
}
