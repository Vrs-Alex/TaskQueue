package com.vrsalex.task_queue.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "task_change_history")
open class TaskChangeHistoryEntity {
    @Id
    @Column(name = "id", nullable = false)
    open var id: UUID? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "task_id", nullable = false)
    open var task: TaskEntity? = null

    @NotNull
    @Column(name = "message", nullable = false, length = Integer.MAX_VALUE)
    open var message: String = ""

    @NotNull
    @Column(name = "\"timestamp\"", nullable = false)
    open var timestamp: OffsetDateTime = OffsetDateTime.now()

}