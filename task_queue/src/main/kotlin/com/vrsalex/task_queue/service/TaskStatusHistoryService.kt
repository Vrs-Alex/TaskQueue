package com.vrsalex.task_queue.service

import com.vrsalex.task_queue.domain.TaskStatusHistory
import java.util.UUID

interface TaskStatusHistoryService {

    fun getTaskHistory(taskId: UUID): List<TaskStatusHistory>

}