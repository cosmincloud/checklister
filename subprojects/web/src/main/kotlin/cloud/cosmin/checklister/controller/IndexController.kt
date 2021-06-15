package cloud.cosmin.checklister.controller

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@Hidden
class IndexController {
    @RequestMapping(path = arrayOf("/"))
    fun index(): String {
        return "redirect:/swagger-ui.html"
    }
}
