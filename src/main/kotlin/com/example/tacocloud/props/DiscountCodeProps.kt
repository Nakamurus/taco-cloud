package com.example.tacocloud.props

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "taco.discount")
data class DiscountCodeProps(
    val codes: HashMap<String, Int> = hashMapOf()
)