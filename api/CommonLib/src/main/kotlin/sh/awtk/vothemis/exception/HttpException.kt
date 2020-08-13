package sh.awtk.vothemis.exception

open class HttpException(val code: Int, val errMessage: String, val clientMessage: String) :
    RuntimeException(errMessage)
