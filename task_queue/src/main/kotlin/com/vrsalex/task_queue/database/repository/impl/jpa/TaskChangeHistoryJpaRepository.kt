package com.vrsalex.task_queue.database.repository.impl.jpa

import com.vrsalex.task_queue.database.entity.TaskChangeHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TaskChangeHistoryJpaRepository : JpaRepository<TaskChangeHistoryEntity, UUID> {

    fun findAllByTaskIdOrderByTimestampAsc(taskId: UUID): List<TaskChangeHistoryEntity>

}