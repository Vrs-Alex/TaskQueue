package com.vrsalex.task_queue.service.impl

import com.vrsalex.task_queue.database.repository.TaskChangedHistoryRepository
import com.vrsalex.task_queue.database.repository.TaskStatusHistoryRepository
import com.vrsalex.task_queue.domain.TaskChangedHistory
import com.vrsalex.task_queue.domain.TaskStatusHistory
import com.vrsalex.task_queue.service.TaskChangedHistoryService
import com.vrsalex.task_queue.service.TaskStatusHistoryService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TaskChangedHistoryServiceImpl(
    private val repository: TaskChangedHistoryRepository
): TaskChangedHistoryService {

    override fun getTaskHistory(taskId: UUID): List<TaskChangedHistory> {
        return repository.findAllByTaskId(taskId)
    }
}