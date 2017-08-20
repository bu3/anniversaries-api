package io.pivotal.anniversaries

class Anniversary (
        val id: Long? = null,
        val name: String,
        val hireDate: String
) {
    constructor() : this(name = "", hireDate = "")
}
