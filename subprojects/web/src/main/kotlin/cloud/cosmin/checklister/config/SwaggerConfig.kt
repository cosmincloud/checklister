package cloud.cosmin.checklister.config

import cloud.cosmin.checklister.service.BuildConfigService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors.basePackage
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun checklisterApi(buildConfigService: BuildConfigService): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(buildConfigService))
                .select()
                    .apis(basePackage("cloud.cosmin.checklister.rest"))
                .build()
    }

    private fun apiInfo(buildConfigService: BuildConfigService): ApiInfo {
        return ApiInfoBuilder()
                .title("Checklister")
                .description("A RESTful API for lists.")
                .contact(Contact("GitHub", "https://github.com/cosmincloud/checklister", null))
                .version(buildConfigService.getVersion())
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build()
    }
}