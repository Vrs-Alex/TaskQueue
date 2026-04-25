package com.vrsalex.task_queue.dto.mapper

import com.vrsalex.task_queue.domain.Task
import com.vrsalex.task_queue.domain.TaskCreate
import com.vrsalex.task_queue.dto.model.CreateTaskRequest
import com.vrsalex.task_queue.dto.model.PageResponse
import com.vrsalex.task_queue.dto.model.TaskResponse
import org.springframework.data.domain.Page


fun CreateTaskRequest.toDomain() = TaskCreate(
    filePath = filePath,
    type = type,
    priority = priority
)

fun Task.toResponse() = TaskResponse(
    id = id,
    filePath = filePath,
    type = type,
    status = status,
    priority = priority,
    retryCount = retryCount,
    errorMessage = errorMessage,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun <T: Any> Page<T>.toPageResponse() = PageResponse(
    content = content,
    page = number,
    size = size,
    totalElements = totalElements,
    totalPages = totalPages
)