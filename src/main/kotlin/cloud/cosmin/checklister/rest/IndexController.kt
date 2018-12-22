package cloud.cosmin.checklister.rest

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import cloud.cosmin.checklister.BuildConfig

@RestController
@ApiIgnore
class IndexController {
    @RequestMapping(path = arrayOf("/"), produces = arrayOf("text/html"))
    fun index(): String {
        val name = BuildConfig.NAME
        val version = BuildConfig.VERSION
        return """
            <html><body>
            <h1>${name}</h1>
            <h2>${version}</h2>
            <p>
                <a href="swagger-ui.html">API</a>
            </p>
            </body></html>
        """.trimIndent()
    }
}
