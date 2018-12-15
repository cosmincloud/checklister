package cloud.cosmin.checklister.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
        val appName = this.javaClass.`package`.implementationTitle
        val version = this.javaClass.`package`.implementationVersion
        val apiInfo = ApiInfo(
                "Checklister API",
                "A RESTful API for managing lists.",
                version,
                "",
                Contact("GitHub", "https://github.com/cosmincloud/checklister", null),
                "Apache License 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                listOf())

        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(basePackage("cloud.cosmin.checklister.rest"))
                .build()
    }
}