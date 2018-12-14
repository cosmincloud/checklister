package cloud.cosmin.checklister.rest

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {
    @RequestMapping(path = arrayOf("/"), produces = arrayOf("text/plain"))
    fun index(): String {
        return "Hello!"
    }
}
