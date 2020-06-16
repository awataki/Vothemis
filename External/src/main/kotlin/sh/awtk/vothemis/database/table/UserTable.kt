package sh.awtk.vothemis.database.table

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.Column

object UserTable : LongIdTable("users") {
    val userName: Column<String> = varchar("user_name", 256).uniqueIndex()
    val hash: Column<String> = varchar("hash", 70)
    val bio: Column<String?> = varchar("bio", 256).nullable()
}

class UserEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(UserTable)

    var name by UserTable.userName
    var hash by UserTable.hash
    var bio by UserTable.bio
}