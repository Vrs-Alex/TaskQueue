package com.vrsalex.task_queue.service

import com.vrsalex.task_queue.domain.TaskChangedHistory
import com.vrsalex.task_queue.domain.TaskStatusHistory
import java.util.UUID

interface TaskChangedHistoryService {

    fun getTaskHistory(taskId: UUID): List<TaskChangedHistory>

}