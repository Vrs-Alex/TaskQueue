package com.vrsalex.task_queue.domain

enum class TaskStatus {
    PENDING,
    IN_PROGRESS,
    DONE,
    FAILED,
    CANCELLED;


    fun canTransitionTo(next: TaskStatus): Boolean = when (this) {
        PENDING -> next == IN_PROGRESS || next == CANCELLED || next == PENDING
        IN_PROGRESS -> next == DONE || next == FAILED || next == CANCELLED || next == PENDING
        DONE -> next == PENDING
        FAILED -> next == PENDING || next == CANCELLED
        CANCELLED  -> false
    }
}