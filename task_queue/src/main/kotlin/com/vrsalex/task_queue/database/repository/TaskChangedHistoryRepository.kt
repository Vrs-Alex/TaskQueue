package com.vrsalex.task_queue.database.repository

import com.vrsalex.task_queue.domain.TaskChangedHistory
import java.util.UUID

interface TaskChangedHistoryRepository {

    fun save(changedHistory: TaskChangedHistory): TaskChangedHistory

    fun findAllByTaskId(taskId: UUID): List<TaskChangedHistory>

}