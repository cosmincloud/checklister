package cloud.cosmin.checklister.rest

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import springfox.documentation.annotations.ApiIgnore

@Controller
@ApiIgnore
class IndexController {
    @RequestMapping(path = arrayOf("/"))
    fun index(): String {
        return "redirect:/swagger-ui.html"
    }
}
