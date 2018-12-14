package cloud.cosmin.checklister.rest

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
@ApiIgnore
class IndexController {
    @RequestMapping(path = arrayOf("/"), produces = arrayOf("text/html"))
    fun index(): String {
        return """
            <html><body>
            <h1>Checklister</h1>
            <p>
                <a href="swagger-ui.html">Swagger UI</a>
            </p>
            </body></html>
        """.trimIndent()
    }
}
