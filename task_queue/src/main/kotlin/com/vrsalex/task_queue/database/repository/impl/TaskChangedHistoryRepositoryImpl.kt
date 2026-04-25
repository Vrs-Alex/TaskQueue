package com.vrsalex.task_queue.database.repository.impl

import com.vrsalex.task_queue.database.mapper.toDomain
import com.vrsalex.task_queue.database.mapper.toEntity
import com.vrsalex.task_queue.database.repository.TaskChangedHistoryRepository
import com.vrsalex.task_queue.database.repository.impl.jpa.TaskChangeHistoryJpaRepository
import com.vrsalex.task_queue.database.repository.impl.jpa.TaskJpaRepository
import com.vrsalex.task_queue.domain.TaskChangedHistory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TaskChangedHistoryRepositoryImpl(
    private val jpa: TaskChangeHistoryJpaRepository,
    private val taskJpaRepository: TaskJpaRepository
): TaskChangedHistoryRepository {


    override fun save(changedHistory: TaskChangedHistory): TaskChangedHistory {
        val taskEntity = taskJpaRepository.findById(changedHistory.taskId)
            .orElseThrow { IllegalArgumentException("Task not found: ${changedHistory.taskId}") }
        return jpa.save(changedHistory.toEntity(taskEntity)).toDomain()
    }

    override fun findAllByTaskId(taskId: UUID): List<TaskChangedHistory> {
        return jpa.findAllByTaskIdOrderByTimestampAsc(taskId).map { it.toDomain() }
    }
}