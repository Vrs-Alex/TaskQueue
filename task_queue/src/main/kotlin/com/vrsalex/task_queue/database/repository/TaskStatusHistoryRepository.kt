package com.vrsalex.task_queue.database.repository

import com.vrsalex.task_queue.domain.TaskStatusHistory
import okhttp3.internal.concurrent.Task
import java.util.UUID

interface TaskStatusHistoryRepository {

    fun save(statusHistory: TaskStatusHistory): TaskStatusHistory

    fun findAllByTaskId(taskId: UUID): List<TaskStatusHistory>

}