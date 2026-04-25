package com.vrsalex.task_queue.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//@Configuration
//class SpringDocConfig {
//
//    @Bean
//    fun openAPI(): OpenAPI = OpenAPI()
//        .info(
//            Info()
//                .title("Task Queue API")
//                .version("1.0.0")
//        )
//
//    @Bean
//    fun pageableOpenApiCustomizer(): OperationCustomizer {
//        return OperationCustomizer { operation, handlerMethod ->
//            operation.parameters?.removeIf { param ->
//                listOf(
//                    "pageable", "sort", "offset", "paged", "unpaged",
//                    "pageNumber", "pageSize"
//                ).contains(param.name)
//            }
//            operation
//        }
//    }
//}