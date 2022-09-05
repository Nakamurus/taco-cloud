package com.example.tacocloud.controller

import com.example.tacocloud.props.DiscountCodeProps
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/discounts")
class DiscountController(
    private val discountCodeProps: DiscountCodeProps
) {
    @GetMapping
    fun displayDiscountCodes(model: Model):String {
        val codes:HashMap<String, Int> = discountCodeProps.codes
        model.addAttribute("codes", codes)

        return "discountList"
    }
}