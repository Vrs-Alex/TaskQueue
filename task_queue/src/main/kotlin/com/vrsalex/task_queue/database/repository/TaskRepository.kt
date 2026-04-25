package com.vrsalex.task_queue.database.repository

import com.vrsalex.task_queue.domain.Task
import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.domain.TaskType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.OffsetDateTime
import java.util.UUID

interface TaskRepository {

    fun save(task: Task): Task

    fun findById(id: UUID): Task?

    fun findWithFilters(
        status: TaskStatus?,
        type: TaskType?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        pageable: Pageable
    ): Page<Task>

    fun findAllByStatus(status: TaskStatus): List<Task>
}