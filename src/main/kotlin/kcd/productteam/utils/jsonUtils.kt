package kcd.productteam.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun <T> String.toJsonObject(type: Class<T>): T {
    val jacksonObjectMapper = getObjectMapper()

    return jacksonObjectMapper.readValue(this, type)
}

private fun getObjectMapper(): ObjectMapper {
    val jacksonObjectMapper = jacksonObjectMapper()
    jacksonObjectMapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
    return jacksonObjectMapper
}