package io.github.bu3.events

import java.time.LocalDate

data class Aggregate(
    var id: String? = null,
    val name: String,
    val photoURL: String?,
    val hireDate: LocalDate
) {
    constructor() : this(name = "", hireDate = LocalDate.MIN, photoURL = null)
}