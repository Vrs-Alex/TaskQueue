package com.vrsalex.task_queue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskQueueApplication

fun main(args: Array<String>) {
	runApplication<TaskQueueApplication>(*args)
}
