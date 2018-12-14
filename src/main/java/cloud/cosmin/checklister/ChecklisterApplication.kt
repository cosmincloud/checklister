package cloud.cosmin.checklister

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ChecklisterApplication

fun main(args: Array<String>) {
    SpringApplication.run(ChecklisterApplication::class.java, *args)
}