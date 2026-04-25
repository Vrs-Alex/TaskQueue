package com.vrsalex.task_queue.database.repository.impl.jpa

import com.vrsalex.task_queue.database.entity.TaskEntity
import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.domain.TaskType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.OffsetDateTime
import java.util.UUID


interface TaskJpaRepository : JpaRepository<TaskEntity, UUID> {

    fun findAllByStatus(status: TaskStatus): List<TaskEntity>

    @Query("""
        SELECT t FROM TaskEntity t
        WHERE (:status IS NULL OR t.status = :status)
          AND (:type IS NULL OR t.type = :type)
          AND (:from IS NULL OR t.createdAt >= :from)
          AND (:to IS NULL OR t.createdAt <= :to)
        ORDER BY t.priority DESC, t.createdAt ASC
    """)
    fun findWithFilters(
        status: TaskStatus?,
        type: TaskType?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        pageable: Pageable
    ): Page<TaskEntity>

}