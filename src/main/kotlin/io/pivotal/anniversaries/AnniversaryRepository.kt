package io.pivotal.anniversaries

import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class AnniversaryDTO (
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        val id: Long? = null,
        @NotNull
        val name: String,
        @NotNull
        val hireDate: String
) {
    constructor() : this(name = "", hireDate = "")
}

interface AnniversaryRepository: JpaRepository<AnniversaryDTO, Long>
