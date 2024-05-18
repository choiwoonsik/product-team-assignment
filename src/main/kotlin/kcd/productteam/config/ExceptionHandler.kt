package kcd.productteam.config

import kcd.productteam.utils.CommonResponse
import kcd.productteam.utils.getFailureResult
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): CommonResponse {
        return getFailureResult(e.message ?: "알 수 없는 오류가 발생했습니다.")
    }
}