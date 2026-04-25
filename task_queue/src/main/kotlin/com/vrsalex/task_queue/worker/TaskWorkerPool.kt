package com.vrsalex.task_queue.worker

import com.vrsalex.task_queue.domain.Task
import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.service.TaskProcessingService
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.concurrent.PriorityBlockingQueue
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.time.Duration.Companion.seconds

@Component
class TaskWorkerPool(
    @Value("\${app.workers.count:3}")
    private val workerCount: Int,
    private val taskProcessingService: TaskProcessingService
) {
    private val queue = PriorityBlockingQueue<Task>(25, compareByDescending { it.priority })

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @PostConstruct
    fun start() {
        repeat(workerCount) { index ->
            scope.launch(CoroutineName("worker-$index")) {
                while (isActive) {
                    val task = queue.take()
                    processTask(task)
                }
            }
        }
    }


    fun enqueue(task: Task): Task {
        val res = taskProcessingService.prepareTask(task)
        queue.put(res)
        return res
    }

    private suspend fun processTask(task: Task) {
        runCatching {
            val processing = taskProcessingService.startProcessing(task)
            delay(Random.nextInt(5..25).seconds)
            val currentValue = taskProcessingService.getTask(processing.id)
            if (currentValue != null && currentValue.status != TaskStatus.CANCELLED) {
            taskProcessingService.completeTask(processing)
                }
        }.onFailure { e ->
            log.error("Ошибка обработки задачи ${task.id}", e)
            taskProcessingService.failTaskSafely(task, e.message ?: "Неизвестная ошибка")
        }
    }


    fun cancelTask(task: Task): Task =
        taskProcessingService.cancelTask(task)



    @PreDestroy
    fun stop() {
        scope.cancel()
    }

    companion object {
        private val log = LoggerFactory.getLogger(TaskWorkerPool::class.java)
    }
}