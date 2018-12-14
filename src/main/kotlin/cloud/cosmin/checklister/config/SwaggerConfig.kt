package cloud.cosmin.checklister.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.RequestHandlerSelectors.basePackage
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun checklisterApi(): Docket {
        val docket = Docket(DocumentationType.SWAGGER_2);
        docket.select()
                .apis(basePackage("cloud.cosmin.checklister.rest"))
                .build()
        return docket
    }
}