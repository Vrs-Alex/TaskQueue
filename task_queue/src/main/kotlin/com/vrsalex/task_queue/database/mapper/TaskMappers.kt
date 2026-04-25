package com.vrsalex.task_queue.database.mapper

import com.vrsalex.task_queue.database.entity.TaskEntity
import com.vrsalex.task_queue.domain.Task
import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.domain.TaskType
import java.time.ZoneOffset

fun Task.toEntity() = TaskEntity().apply {
    id = this@toEntity.id
    filePath = this@toEntity.filePath
    status = this@toEntity.status.name
    type = this@toEntity.type.name
    priority = this@toEntity.priority
    retryCount = this@toEntity.retryCount
    errorMessage = this@toEntity.errorMessage
    createdAt = this@toEntity.createdAt.atOffset(ZoneOffset.UTC)
    updatedAt = this@toEntity.updatedAt.atOffset(ZoneOffset.UTC)
}

fun TaskEntity.toDomain() = Task(
    id = id!!,
    filePath = filePath,
    status = TaskStatus.valueOf(status),
    type = TaskType.valueOf(type),
    priority = priority,
    retryCount = retryCount,
    errorMessage = errorMessage,
    createdAt = createdAt.toInstant(),
    updatedAt = updatedAt.toInstant()
)