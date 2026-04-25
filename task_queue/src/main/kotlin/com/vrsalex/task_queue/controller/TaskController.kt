package com.vrsalex.task_queue.controller

import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.domain.TaskType
import com.vrsalex.task_queue.dto.mapper.toDomain
import com.vrsalex.task_queue.dto.mapper.toPageResponse
import com.vrsalex.task_queue.dto.mapper.toResponse
import com.vrsalex.task_queue.dto.model.CreateTaskRequest
import com.vrsalex.task_queue.dto.model.PageResponse
import com.vrsalex.task_queue.dto.model.TaskResponse
import com.vrsalex.task_queue.service.TaskService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime
import java.util.UUID

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новую задачу")
    fun createTask(@RequestBody request: CreateTaskRequest): TaskResponse =
        taskService.createTask(request.toDomain()).toResponse()


    @GetMapping("/{id}")
    @Operation(summary = "Получить задачу по идентификатору")
    fun getTask(@PathVariable id: UUID): TaskResponse =
        taskService.getTask(id).toResponse()


    @GetMapping
    @Operation(summary = "Получить список задач")
    fun listTasks(
        @RequestParam status: TaskStatus? = null,
        @RequestParam type: TaskType? = null,
        @RequestParam from: OffsetDateTime? = null,
        @RequestParam to: OffsetDateTime? = null,
        @PageableDefault(size = 50)
        pageable: Pageable
    ): PageResponse<TaskResponse> {
        return taskService.listTasks(status, type, from, to, pageable)
            .map { it.toResponse() }
            .toPageResponse()
    }


    @PostMapping("/{id}")
    @Operation(summary = "Отправить задачу на выполнение")
    fun submitTask(@PathVariable id: UUID): TaskResponse =
        taskService.submitTask(id).toResponse()


    @PostMapping("/recover")
    @Operation(summary = "Перезапустить застрявшие задачи")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun recoverStuckTasks() =
        taskService.recoverStuckTasks()

    @PostMapping("/recover-failed")
    @Operation(summary = "Восстановить упавшие задачи")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun recoverFailedTasks() =
        taskService.recoverFailedTasks()


    @DeleteMapping("/{id}")
    @Operation(summary = "Отменить задачу")
    fun cancelTask(@PathVariable id: UUID): TaskResponse =
        taskService.cancelTask(id).toResponse()


}