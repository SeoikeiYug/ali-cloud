package com.genius.cloud.config.swagger;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableOpenApi
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(
                // 设置使用 OpenApi 3.0 规范
                DocumentationType.OAS_30)
                // 是否开启 Swagger
                .enable(true)
                // 配置项目基本信息
                .apiInfo(apiInfo())
                // 选择那些路径和api会生成document
                .select()
                // 对所有api进行监控
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 如果需要指定对某个包的接口进行监控，则可以配置如下
                // .apis(RequestHandlerSelectors.basePackage("com.pay.server.controller"))
                // 对所有路径进行监控
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档标题
                .title("ZPAY Payment Interface")
                // 文档描述
                .description("Payment Related Operation Interface")
                // 文档版本
                .version("1.0.0")
                // 设置许可声明信息
                .license("GENIUS")
                // 设置许可证URL地址
                .licenseUrl("http://liulianhaochi.xyz")
                // 设置管理该API人员的联系信息
                .contact(new Contact("GENIUS", "http://liulianhaochi.xyz", "yuseoikei@gmail.com"))
                .build();
    }

}
