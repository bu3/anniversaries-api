package io.pivotal.anniversaries

import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Anniversary(
        @Id
        @GeneratedValue
        var id: Long = 0,

        @NotNull
        val name: String,
        @NotNull
        val hireDate: LocalDate,
        @NotNull
        var anniversaryDate: LocalDate
) {
    constructor() : this(name = "", hireDate = LocalDate.MIN, anniversaryDate = LocalDate.MAX)
}

interface AnniversaryRepository : JpaRepository<Anniversary, Long> {
    fun findAllByOrderByAnniversaryDateAsc() : List<Anniversary>
}