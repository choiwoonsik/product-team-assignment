package kcd.productteam

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class Hello {

    @GetMapping("/hello")
    fun hello(): String {
        return "Hello, World!"
    }
}