package sh.awtk.vothemis.exception


class ForbiddenException(message: String, cMessage: String) : HttpException(403, message, cMessage)