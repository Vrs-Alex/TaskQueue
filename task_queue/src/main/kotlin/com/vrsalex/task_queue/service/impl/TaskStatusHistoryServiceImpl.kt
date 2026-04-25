package com.vrsalex.task_queue.service.impl

import com.vrsalex.task_queue.database.repository.TaskStatusHistoryRepository
import com.vrsalex.task_queue.domain.TaskStatusHistory
import com.vrsalex.task_queue.service.TaskStatusHistoryService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TaskStatusHistoryServiceImpl(
    private val repository: TaskStatusHistoryRepository
): TaskStatusHistoryService {

    override fun getTaskHistory(taskId: UUID): List<TaskStatusHistory> {
        return repository.findAllByTaskId(taskId)
    }
}