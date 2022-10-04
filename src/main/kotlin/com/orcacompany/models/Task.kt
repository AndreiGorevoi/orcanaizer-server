@file:OptIn(ExperimentalSerializationApi::class)

package com.orcacompany.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    @Serializable(with = PrioritySerializer::class)
    val priority: Priority,
    @Serializable(with = DateSerializer::class)
    val dueDate: LocalDateTime,
    val userId: Int,
    val completed: Boolean = false
)

object Tasks : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val description = varchar("description", 1024)
    val priority = short("priority")
    val dueDate = datetime("dueDate")
    val userId = integer("userId")
    val completed = bool("completed")

    override val primaryKey = PrimaryKey(id)
}

@Serializer(forClass = LocalDateTime::class)
object DateSerializer : KSerializer<LocalDateTime> {

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString())
    }

}

@Serializer(forClass = Priority::class)
object PrioritySerializer : KSerializer<Priority> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Priority", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): Priority {
        return Priority.priorityFromInt(decoder.decodeInt())
    }

    override fun serialize(encoder: Encoder, value: Priority) {
        encoder.encodeInt(value.squareNumber)
    }

}