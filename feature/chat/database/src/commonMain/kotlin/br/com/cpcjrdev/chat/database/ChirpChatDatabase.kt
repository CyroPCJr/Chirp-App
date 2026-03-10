package br.com.cpcjrdev.chat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.cpcjrdev.chat.database.dao.ChatDao
import br.com.cpcjrdev.chat.database.dao.ChatMessageDao
import br.com.cpcjrdev.chat.database.dao.ChatParticipantDao
import br.com.cpcjrdev.chat.database.dao.ChatParticipantsCrossRefDao
import br.com.cpcjrdev.chat.database.entities.ChatEntity
import br.com.cpcjrdev.chat.database.entities.ChatMessageEntity
import br.com.cpcjrdev.chat.database.entities.ChatParticipantCrossRef
import br.com.cpcjrdev.chat.database.entities.ChatParticipantEntity
import br.com.cpcjrdev.chat.database.view.LastMessageView

@Database(
    entities = [
        ChatEntity::class,
        ChatParticipantEntity::class,
        ChatMessageEntity::class,
        ChatParticipantCrossRef::class,
    ],
    views = [
        LastMessageView::class,
    ],
    version = 1,
)
abstract class ChirpChatDatabase : RoomDatabase() {
    abstract val chatDao: ChatDao
    abstract val chatParticipantDao: ChatParticipantDao
    abstract val chatMessageDao: ChatMessageDao
    abstract val chatParticipantsCrossRefDao: ChatParticipantsCrossRefDao

    companion object {
        const val DB_NAME = "chirp.db"
    }
}
