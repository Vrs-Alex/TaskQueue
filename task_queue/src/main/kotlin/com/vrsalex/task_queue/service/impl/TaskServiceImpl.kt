package com.vrsalex.task_queue.service.impl

import com.vrsalex.task_queue.database.repository.TaskChangedHistoryRepository
import com.vrsalex.task_queue.database.repository.TaskRepository
import com.vrsalex.task_queue.database.repository.TaskStatusHistoryRepository
import com.vrsalex.task_queue.domain.Task
import com.vrsalex.task_queue.domain.TaskCreate
import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.domain.TaskStatusHistory
import com.vrsalex.task_queue.domain.TaskType
import com.vrsalex.task_queue.service.TaskService
import com.vrsalex.task_queue.worker.TaskWorkerPool
import jakarta.annotation.PostConstruct
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.OffsetDateTime
import java.util.UUID

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val taskStatusHistoryRepository: TaskStatusHistoryRepository,
    private val taskChangedHistoryRepository: TaskChangedHistoryRepository,
    private val workerPool: TaskWorkerPool
) : TaskService {

    override fun createTask(taskCreate: TaskCreate): Task {
        val task = Task(
            id = UUID.randomUUID(),
            filePath = taskCreate.filePath,
            type = taskCreate.type,
            status = TaskStatus.PENDING,
            priority = taskCreate.priority,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            retryCount = 0,
            errorMessage = null
        )
        val saved = taskRepository.save(task)
        workerPool.enqueue(saved)
        return saved
    }

    override fun getTask(id: UUID): Task {
        return taskRepository.findById(id)
            ?: throw NoSuchElementException("Задача не найдена: $id")
    }

    override fun listTasks(
        status: TaskStatus?,
        type: TaskType?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        pageable: Pageable
    ): Page<Task> =
        taskRepository.findWithFilters(status, type, from, to, pageable)


    override fun cancelTask(id: UUID): Task {
        val task = getTask(id)
        return workerPool.cancelTask(task)
    }

    override fun submitTask(id: UUID): Task {
        val task = getTask(id)
        if (task.status == TaskStatus.IN_PROGRESS) {
            throw IllegalStateException("Задача уже выполняется")
        }
        val updated = task.copy(retryCount = task.retryCount + 1)
        return workerPool.enqueue(updated)
    }

    @PostConstruct
    override fun recoverStuckTasks() {
        taskRepository.findAllByStatus(TaskStatus.PENDING).forEach { task ->
            workerPool.enqueue(task.copy(status = TaskStatus.FAILED))
        }

        taskRepository.findAllByStatus(TaskStatus.IN_PROGRESS).forEach { task ->
            workerPool.enqueue(task)
        }
    }

    override fun recoverFailedTasks() {
        taskRepository.findAllByStatus(TaskStatus.FAILED).forEach { task ->
            workerPool.enqueue(task)
        }
    }
}