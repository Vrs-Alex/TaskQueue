package com.vrsalex.task_queue.service

import com.vrsalex.task_queue.domain.Task
import com.vrsalex.task_queue.domain.TaskCreate
import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.domain.TaskType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.OffsetDateTime
import java.util.UUID


interface TaskService {

    fun createTask(taskCreate: TaskCreate): Task

    fun getTask(id: UUID): Task

    fun listTasks(
        status: TaskStatus?,
        type: TaskType?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        pageable: Pageable
    ): Page<Task>

    fun cancelTask(id: UUID): Task

    fun submitTask(id: UUID): Task

    fun recoverStuckTasks()

    fun recoverFailedTasks()

}