package com.vrsalex.task_queue.service

import com.vrsalex.task_queue.domain.Task
import java.util.UUID

interface TaskProcessingService {

    fun getTask(taskId: UUID): Task?

    fun prepareTask(task: Task): Task

    fun startProcessing(task: Task): Task

    fun completeTask(task: Task): Task

    fun cancelTask(task: Task): Task

    fun failTask(task: Task, error: String): Task

    fun failTaskSafely(task: Task, error: String): Task?

}