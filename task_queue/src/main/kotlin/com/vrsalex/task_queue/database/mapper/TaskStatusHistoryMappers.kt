package com.vrsalex.task_queue.database.mapper

import com.vrsalex.task_queue.database.entity.TaskEntity
import com.vrsalex.task_queue.database.entity.TaskStatusHistoryEntity
import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.domain.TaskStatusHistory
import java.time.ZoneOffset

fun TaskStatusHistory.toEntity(taskEntity: TaskEntity) = TaskStatusHistoryEntity().apply {
    id = this@toEntity.id
    task = taskEntity
    status = this@toEntity.status.name
    timestamp = this@toEntity.timestamp.atOffset(ZoneOffset.UTC)
    message = this@toEntity.message
}

fun TaskStatusHistoryEntity.toDomain() = TaskStatusHistory(
    id = id!!,
    taskId = task!!.id!!,
    status = TaskStatus.valueOf(status),
    timestamp = timestamp.toInstant(),
    message = message
)