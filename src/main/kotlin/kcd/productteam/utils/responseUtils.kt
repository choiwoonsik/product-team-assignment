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

data class MultiResponse<T>(
    val success: Boolean,
    val message: String,
    val itemList: List<T>,
)

data class SingleResponse<T>(
    val success: Boolean,
    val message: String,
    val item: T,
)

fun <T> getMultiResult(itemList: List<T>): MultiResponse<T> {
    return MultiResponse(success = true, message = "성공", itemList = itemList)
}

fun <T> getSingleResult(item: T): SingleResponse<T> {
    return SingleResponse(success = true, message = "성공", item = item)
}