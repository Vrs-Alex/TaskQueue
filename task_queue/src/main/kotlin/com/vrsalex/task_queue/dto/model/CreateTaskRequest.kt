package com.vrsalex.task_queue.dto.model

import com.vrsalex.task_queue.domain.TaskType
import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest(
    val filePath: String,
    val type: TaskType,
    val priority: Int = 0
)