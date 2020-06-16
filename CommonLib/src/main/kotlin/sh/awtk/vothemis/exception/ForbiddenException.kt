package sh.awtk.vothemis.exception


class ForbiddenException(message: String) : HttpException(403, message)