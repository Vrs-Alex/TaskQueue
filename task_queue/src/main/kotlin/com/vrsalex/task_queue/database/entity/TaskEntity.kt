package com.vrsalex.task_queue.database.entity

import com.vrsalex.task_queue.domain.TaskStatus
import com.vrsalex.task_queue.domain.TaskType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "task")
open class TaskEntity {
    @Id
    @Column(name = "id", nullable = false)
    open var id: UUID? = null

    @NotNull
    @Column(name = "file_path", nullable = false, length = Integer.MAX_VALUE)
    open var filePath: String = ""

    @Size(max = 50)
    @NotNull
    @Column(name = "type", nullable = false, length = 50)
    open var type: String = ""

    @Size(max = 50)
    @NotNull
    @Column(name = "status", nullable = false, length = 50)
    open var status: String = ""

    @NotNull
    @ColumnDefault("0")
    @Column(name = "priority", nullable = false)
    open var priority: Int = 0

    @NotNull
    @ColumnDefault("0")
    @Column(name = "retry_count", nullable = false)
    open var retryCount: Int = 0

    @Column(name = "error_message", length = Integer.MAX_VALUE)
    open var errorMessage: String? = null

    @NotNull
    @Column(name = "created_at", nullable = false)
    open var createdAt: OffsetDateTime = OffsetDateTime.now()

    @NotNull
    @Column(name = "updated_at", nullable = false)
    open var updatedAt: OffsetDateTime = OffsetDateTime.now()

}