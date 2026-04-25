package com.vrsalex.task_queue.database.repository.impl.jpa

import com.vrsalex.task_queue.database.entity.TaskStatusHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TaskStatusHistoryJpaRepository : JpaRepository<TaskStatusHistoryEntity, UUID> {

    fun findAllByTaskIdOrderByTimestampAsc(taskId: UUID): List<TaskStatusHistoryEntity>

}