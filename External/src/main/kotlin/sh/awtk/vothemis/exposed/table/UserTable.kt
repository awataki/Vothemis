package sh.awtk.vothemis.exposed.table

import UserPass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.Column
import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.vo.UserBio
import sh.awtk.vothemis.vo.UserId
import sh.awtk.vothemis.vo.UserName

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
    val questions by QuestionEntity referrersOn QuestionTable.createdBy

    fun toUserId(): UserId {
        return UserId(id.value)
    }

    fun toDto(): UserDto {
        return UserDto(
            id = UserId(id.value),
            name = UserName(name),
            password = UserPass(hash),
            bio = UserBio(bio)
        )
    }
}