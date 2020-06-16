package sh.awtk.vothemis.exception

class AuthenticationException(message: String) : HttpException(401,message)