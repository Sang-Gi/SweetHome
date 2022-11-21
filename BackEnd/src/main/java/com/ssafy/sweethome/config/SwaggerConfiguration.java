package com.ssafy.sweethome.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private ApiInfo apiInfo() {
        String version = "v1";
        String title = "SweetHome API " + version;
        return new ApiInfoBuilder().title(title).build();
    }

    @Bean
    public Docket userApi() {
        return getDocket("회원", Predicates.or(PathSelectors.regex("/user.*")));
    }

    @Bean
    public Docket adminApi() {
        return getDocket("관리자", Predicates.or(PathSelectors.regex("/admin.*")));
    }

    @Bean
    public Docket boardApi() {
        return getDocket("게시판", Predicates.or(PathSelectors.regex("/board.*")));
    }

    @Bean
    public Docket noticeApi() {
        return getDocket("공지사항", Predicates.or(PathSelectors.regex("/notice.*")));
    }

    @Bean
    public Docket favoriteRegionApi() {
        return getDocket("관심 지역", Predicates.or(PathSelectors.regex("/favorite-region.*")));
    }

    @Bean
    public Docket favoriteHouseApi() {
        return getDocket("관심 거래", Predicates.or(PathSelectors.regex("/favorite-house.*")));
    }

    @Bean
    public Docket commentApi() {
        return getDocket("댓글", Predicates.or(PathSelectors.regex("/comment.*")));
    }

    @Bean
    public Docket houseApi() {
        return getDocket("아파트 거래", Predicates.or(PathSelectors.regex("/house.*")));

    }

    @Bean
    public Docket allApi() {
        return getDocket("전체", Predicates.or(PathSelectors.regex("/*.*")));
    }

    public Docket getDocket(String groupName, Predicate<String> predicate) {
        return new Docket(DocumentationType.SWAGGER_2).groupName(groupName).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.sweethome.*.controller")).paths(predicate)
                .apis(RequestHandlerSelectors.any()).build();
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder().displayRequestDuration(true).validatorUrl("").build();
    }
}
