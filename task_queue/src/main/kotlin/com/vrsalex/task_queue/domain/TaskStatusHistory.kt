package com.vrsalex.task_queue.domain

import java.time.Instant
import java.util.UUID

data class TaskStatusHistory(
    val id: UUID,
    val taskId: UUID,
    val status: TaskStatus,
    val timestamp: Instant,
    val message: String?
)
