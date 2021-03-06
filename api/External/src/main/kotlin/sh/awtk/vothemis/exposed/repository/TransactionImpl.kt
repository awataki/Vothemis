package sh.awtk.vothemis.exposed.repository

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import sh.awtk.vothemis.exposed.DatabaseFactory
import sh.awtk.vothemis.interfaces.repository.ITransaction
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class TransactionImpl : ITransaction {
    private val dispatcher: CoroutineContext

    init {
        dispatcher = Executors.newFixedThreadPool(DatabaseFactory.maxPoolSize).asCoroutineDispatcher()
    }

    override suspend fun <T> run(codeBlock: () -> T): T =
        withContext(dispatcher) {
            transaction {
                codeBlock()
            }
        }

    override suspend fun <T> suspendRun(codeBlock: suspend () -> T): T =
        withContext(dispatcher) {
            newSuspendedTransaction {
                codeBlock()
            }
        }
}