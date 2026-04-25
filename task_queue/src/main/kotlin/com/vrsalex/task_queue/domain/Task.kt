package com.vrsalex.task_queue.domain

import java.time.Instant
import java.util.UUID
import kotlin.uuid.Uuid

data class Task(
    val id: UUID,
    val filePath: String,
    val type: TaskType,
    val status: TaskStatus,
    val priority: Int,
    val retryCount: Int,
    val errorMessage: String?,
    val createdAt: Instant,
    val updatedAt: Instant
){
    fun transitionTo(next: TaskStatus): Task {
        require(status.canTransitionTo(next)) {
            "Статус задачи не может быть изменен с $status на $next"
        }
        return copy(status = next, updatedAt = Instant.now())
    }
}

data class TaskCreate(
    val filePath: String,
    val type: TaskType,
    val priority: Int
)