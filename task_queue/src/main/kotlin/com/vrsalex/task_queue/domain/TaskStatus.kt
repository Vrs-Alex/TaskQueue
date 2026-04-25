package com.vrsalex.task_queue.domain

enum class TaskStatus {
    PENDING,
    IN_PROGRESS,
    DONE,
    FAILED,
    CANCELLED
}