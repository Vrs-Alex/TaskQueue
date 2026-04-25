package com.vrsalex.task_queue.domain

import java.time.Instant
import java.util.UUID

data class TaskChangedHistory(
    val id: UUID,
    val taskId: UUID,
    val message: String,
    val timestamp: Instant
)
