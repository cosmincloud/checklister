package cloud.cosmin.checklister.config

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
    fun checklisterApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                    .apis(basePackage("cloud.cosmin.checklister.rest"))
                .build()
    }

    fun apiInfo(): ApiInfo {
        val appName = this.javaClass.`package`.implementationTitle
        val version = this.javaClass.`package`.implementationVersion
        return ApiInfoBuilder()
                .title("Checklister API")
                .description("A RESTful API for lists.")
                .contact(Contact("GitHub", "https://github.com/cosmincloud/checklister", null))
                .version(version)
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build()
    }
}