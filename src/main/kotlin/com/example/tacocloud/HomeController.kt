package com.example.tacocloud

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller // 機能的にはビーンだと示すだけで@Componentアノテーションでも代替可能だが、人間にとってより適切な説明
class HomeController {

    @GetMapping("/")
    fun home():String {
        return "home"
    }
}