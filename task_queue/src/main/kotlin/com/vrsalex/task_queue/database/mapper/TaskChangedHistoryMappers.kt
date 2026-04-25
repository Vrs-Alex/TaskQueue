package com.vrsalex.task_queue.database.mapper

import com.vrsalex.task_queue.database.entity.TaskChangeHistoryEntity
import com.vrsalex.task_queue.database.entity.TaskEntity
import com.vrsalex.task_queue.domain.TaskChangedHistory
import java.time.ZoneOffset

fun TaskChangedHistory.toEntity(taskEntity: TaskEntity) = TaskChangeHistoryEntity().apply {
    id = this@toEntity.id
    task = taskEntity
    message = this@toEntity.message
    timestamp = this@toEntity.timestamp.atOffset(ZoneOffset.UTC)
}

fun TaskChangeHistoryEntity.toDomain() = TaskChangedHistory(
    id = id!!,
    taskId = task!!.id!!,
    message = message,
    timestamp = timestamp.toInstant()
)