package com.wepat.config;

import org.apache.coyote.Request;
import org.springframework.cglib.proxy.Factory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private String version;
    private String title;

    private ApiInfo appiInfo() {
        return new ApiInfoBuilder().title("WePat 사용자관리 API")
                .description("<h3>RestApi에 대한 문서를 제공한다.</h3>")
                .contact(new Contact("WePat", "https://www.wepat.com", "wepat@wepat.com")).license("MIT License")
                .version("1.0").build();
    }
    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2) // Swagger 2.0 기반의 문서 작성
                .apiInfo(this.appiInfo()) // 문서에 대한 정보를 설정한다.
                .select() // ApiSelectorBuilder를 반환하며 상세한 설정 처리
                .apis(RequestHandlerSelectors.any())// 대상으로하는 api 설정 -> 모든 controller
                .paths(PathSelectors.any()) // controller에서 swagger를 지정할 대상 path 설정
                .build();  // Docket 객체 생성
    }
}