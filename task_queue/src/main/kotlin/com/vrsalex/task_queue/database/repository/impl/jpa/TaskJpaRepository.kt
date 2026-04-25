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

    fun findAllByStatus(status: String): List<TaskEntity>

    @Query("""
    SELECT * FROM task
    WHERE (CAST(:status AS VARCHAR) IS NULL OR status = :status)
      AND (CAST(:type AS VARCHAR) IS NULL OR type = :type)
      AND (CAST(:from AS TIMESTAMPTZ) IS NULL OR created_at >= :from)
      AND (CAST(:to AS TIMESTAMPTZ) IS NULL OR created_at <= :to)
    ORDER BY priority DESC, created_at DESC
""", nativeQuery = true)
    fun findWithFilters(
        @Param("status") status: String?,
        @Param("type") type: String?,
        @Param("from") from: OffsetDateTime?,
        @Param("to") to: OffsetDateTime?,
        pageable: Pageable
    ): Page<TaskEntity>

}