package com.vrsalex.task_queue.controller

import com.vrsalex.task_queue.domain.TaskChangedHistory
import com.vrsalex.task_queue.domain.TaskStatusHistory
import com.vrsalex.task_queue.service.TaskChangedHistoryService
import com.vrsalex.task_queue.service.TaskStatusHistoryService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/history")
class TaskHistoryController(
    private val statusHisService: TaskStatusHistoryService,
    private val changedHisService: TaskChangedHistoryService
) {

    @Operation(summary = "Получить историю статусов задачи по идентификатору задачи")
    @GetMapping("status/{id}")
    fun getTaskHistory(
        @PathVariable id: UUID
    ): List<TaskStatusHistory> = statusHisService.getTaskHistory(id)

    @Operation(summary = "Получить историю изменений задачи по идентификатору задачи")
    @GetMapping("changed/{id}")
    fun getTaskChangedHistory(
        @PathVariable id: UUID
    ): List<TaskChangedHistory> = changedHisService.getTaskHistory(id)


}