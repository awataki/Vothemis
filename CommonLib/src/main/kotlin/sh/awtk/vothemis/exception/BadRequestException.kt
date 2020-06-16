package sh.awtk.vothemis.exception

class BadRequestException(message: String) : HttpException(400, message)