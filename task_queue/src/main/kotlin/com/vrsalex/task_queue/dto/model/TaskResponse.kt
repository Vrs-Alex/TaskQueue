package com.vrsalex.task_queue.dto.model

import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.domain.TaskType
import com.vrsalex.task_queue.dto.serializer.InstantSerializer
import com.vrsalex.task_queue.dto.serializer.UuidSerializer
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.UUID

@Serializable
data class TaskResponse(
    @Serializable(with = UuidSerializer::class)
    val id: UUID,
    val filePath: String,
    val type: TaskType,
    val status: TaskStatus,
    val priority: Int,
    val retryCount: Int,
    val errorMessage: String?,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant
)