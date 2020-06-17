package sh.awtk.vothemis.interfaces.repository

interface ITransaction {
    suspend fun <T> run(codeBlock: () -> T): T
    suspend fun <T> suspendRun(codeBlock: suspend () -> T): T
}