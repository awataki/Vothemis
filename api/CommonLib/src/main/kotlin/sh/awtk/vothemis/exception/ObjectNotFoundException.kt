package sh.awtk.vothemis.exception

class ObjectNotFoundException(message: String, cMessage: String) : HttpException(404, message, cMessage)