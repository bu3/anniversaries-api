package io.github.bu3.employees

import java.time.LocalDate
import java.util.UUID

data class Aggregate(
    val id: Long? = null,
    val name: String,
    val photoURL: String?,
    val hireDate: LocalDate
) {
    val aggregateId: String
        get() = UUID.randomUUID().toString()

    constructor() : this(name = "", hireDate = LocalDate.MIN, photoURL = null)
}