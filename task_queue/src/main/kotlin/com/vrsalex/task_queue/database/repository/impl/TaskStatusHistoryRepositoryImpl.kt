package com.vrsalex.task_queue.database.repository.impl

import com.vrsalex.task_queue.database.mapper.toDomain
import com.vrsalex.task_queue.database.mapper.toEntity
import com.vrsalex.task_queue.database.repository.TaskStatusHistoryRepository
import com.vrsalex.task_queue.database.repository.impl.jpa.TaskJpaRepository
import com.vrsalex.task_queue.database.repository.impl.jpa.TaskStatusHistoryJpaRepository
import com.vrsalex.task_queue.domain.TaskStatusHistory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TaskStatusHistoryRepositoryImpl(
    private val jpa: TaskStatusHistoryJpaRepository,
    private val taskJpaRepository: TaskJpaRepository
): TaskStatusHistoryRepository {


    override fun save(statusHistory: TaskStatusHistory): TaskStatusHistory {
        val taskEntity = taskJpaRepository.findById(statusHistory.taskId)
            .orElseThrow { IllegalArgumentException("Task not found: ${statusHistory.taskId}") }
        return jpa.save(statusHistory.toEntity(taskEntity)).toDomain()
    }

    override fun findAllByTaskId(taskId: UUID): List<TaskStatusHistory> {
        return jpa.findAllByTaskIdOrderByTimestampAsc(taskId).map { it.toDomain() }
    }
}