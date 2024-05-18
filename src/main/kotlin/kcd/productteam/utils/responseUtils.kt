package kcd.productteam.utils

fun getSuccessResult(): CommonResponse {
    return CommonResponse(success = true, message = "성공")
}

fun getFailureResult(message: String): CommonResponse {
    return CommonResponse(success = false, message = message)
}

data class CommonResponse(
    val success: Boolean,
    val message: String,
)