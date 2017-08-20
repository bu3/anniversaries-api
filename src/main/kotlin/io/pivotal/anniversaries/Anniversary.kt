package io.pivotal.anniversaries

import java.time.LocalDate

class Anniversary (
        val id: Long? = null,
        val name: String,
        val hireDate: LocalDate
) {

    lateinit var anniversaryDate: LocalDate

    constructor() : this(name = "", hireDate = LocalDate.MIN)
}
