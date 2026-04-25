package com.vrsalex.task_queue.database.repository.impl

import com.vrsalex.task_queue.database.mapper.toDomain
import com.vrsalex.task_queue.database.mapper.toEntity
import com.vrsalex.task_queue.database.repository.TaskRepository
import com.vrsalex.task_queue.database.repository.impl.jpa.TaskJpaRepository
import com.vrsalex.task_queue.domain.Task
import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.domain.TaskType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.UUID

@Repository
class TaskRepositoryImpl(
    private val jpa: TaskJpaRepository
) : TaskRepository {

    override fun save(task: Task): Task =
        jpa.save(task.toEntity()).toDomain()

    override fun findById(id: UUID): Task? =
        jpa.findById(id).orElse(null)?.toDomain()

    override fun findWithFilters(
        status: TaskStatus?,
        type: TaskType?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        pageable: Pageable
    ): Page<Task> =
        jpa.findWithFilters(status?.name, type?.name, from, to, pageable)
            .map { it.toDomain() }

    override fun findAllByStatus(status: TaskStatus): List<Task> =
        jpa.findAllByStatus(status.name).map { it.toDomain() }
}