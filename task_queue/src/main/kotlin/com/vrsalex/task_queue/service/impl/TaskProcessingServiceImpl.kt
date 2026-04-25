package com.vrsalex.task_queue.service.impl

import com.vrsalex.task_queue.database.repository.TaskChangedHistoryRepository
import com.vrsalex.task_queue.database.repository.TaskRepository
import com.vrsalex.task_queue.database.repository.TaskStatusHistoryRepository
import com.vrsalex.task_queue.domain.Task
import com.vrsalex.task_queue.domain.TaskChangedHistory
import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.domain.TaskStatusHistory
import com.vrsalex.task_queue.service.TaskProcessingService
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*


@Service
class TaskProcessingServiceImpl(
    private val taskRepository: TaskRepository,
    private val taskStatusHistoryRepository: TaskStatusHistoryRepository,
    private val taskChangedHistoryRepository: TaskChangedHistoryRepository
) : TaskProcessingService {



    override fun getTask(taskId: UUID): Task? =
        taskRepository.findById(taskId)

    override fun prepareTask(task: Task): Task {
        val updated = task.transitionTo(TaskStatus.PENDING)
        saveHistory(updated)
        saveChanged(updated, "Задача подготовлена и ожидает обработки")
        taskRepository.save(updated)
        return updated
    }

    override fun startProcessing(task: Task): Task {
        val updated = task.transitionTo(TaskStatus.IN_PROGRESS)
        saveHistory(updated)
        saveChanged(updated, "Задача в обработке")
        return taskRepository.save(updated)
    }

    override fun completeTask(task: Task): Task {
        val updated = task.transitionTo(TaskStatus.DONE)
        saveHistory(updated)
        saveChanged(updated, "Задача успешно выполнена")
        return taskRepository.save(updated)
    }

    override fun cancelTask(task: Task): Task {
        if (task.status == TaskStatus.DONE) {
            throw IllegalStateException("Невозможно отменить завершенную задачу: ${task.id}")
        }
        val updated = task.transitionTo(TaskStatus.CANCELLED)
        saveHistory(updated)
        saveChanged(updated, "Задача отменена")
        return taskRepository.save(updated)
    }

    override fun failTask(task: Task, error: String): Task {
        val updated = task.transitionTo(TaskStatus.FAILED).copy(errorMessage = error)
        saveHistory(updated)
        saveChanged(updated, "Задача завершилась с ошибкой: $error")
        return taskRepository.save(updated)
    }

    override fun failTaskSafely(task: Task, error: String): Task? {
        if (!task.status.canTransitionTo(TaskStatus.FAILED)) {
            return null
        }
        return failTask(task, error)
    }

    private fun saveChanged(task: Task, message: String) {
        taskChangedHistoryRepository.save(
            TaskChangedHistory(
                id = UUID.randomUUID(),
                taskId = task.id,
                message = message,
                timestamp = Instant.now()
            )
        )
    }

    private fun saveHistory(task: Task) {
        taskStatusHistoryRepository.save(
            TaskStatusHistory(
                id = UUID.randomUUID(),
                taskId = task.id,
                status = task.status,
                timestamp = Instant.now(),
                message = "Статус изменен: ${task.status}."
            )
        )
    }
}