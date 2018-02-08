package io.github.bu3.anniversaries

import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.transaction.Transactional

@Entity
data class Anniversary(
    @Id
    @GeneratedValue
    var id: Long = 0,

    @NotNull
    val employeeId: String,

    @NotNull
    val name: String,

    @NotNull
    val hireDate: LocalDate,

    @NotNull
    var anniversaryDate: LocalDate,

    @NotNull
    val photoURL: String?
) {
    constructor() : this(name = "", employeeId = "", hireDate = LocalDate.MIN, anniversaryDate = LocalDate.MAX, photoURL = null)
}

interface AnniversaryRepository : JpaRepository<Anniversary, Long> {
    fun findAllByOrderByAnniversaryDateAsc(): List<Anniversary>

    fun findByAnniversaryDateLessThanOrderByAnniversaryDateAsc(anniversaryDate: LocalDate): List<Anniversary>

    @Transactional
    fun deleteByEmployeeId(aggregateId: String): Long
}