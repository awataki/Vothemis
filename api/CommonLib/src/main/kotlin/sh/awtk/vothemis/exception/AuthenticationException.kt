package sh.awtk.vothemis.exception

class AuthenticationException(message: String, cMessage: String) : HttpException(401, message, cMessage)