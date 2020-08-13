package sh.awtk.vothemis.exception

class ObjectNotFoundExcepiton(message: String, cMessage: String) : HttpException(404, message, cMessage)