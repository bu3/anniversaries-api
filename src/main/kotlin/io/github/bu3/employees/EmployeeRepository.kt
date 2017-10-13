package io.github.bu3.employees

import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class Employee(
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        val id: Long? = null,
        @NotNull
        val name: String,
        @NotNull
        val hireDate: LocalDate
) {
    constructor() : this(name = "", hireDate = LocalDate.MIN)
}

interface EmployeeRepository : JpaRepository<Employee, Long>
