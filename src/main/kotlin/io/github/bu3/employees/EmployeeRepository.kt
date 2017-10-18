package io.github.bu3.employees

import org.hibernate.validator.constraints.URL
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull


@Entity
data class Employee(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,

        @field:NotNull
        val name: String,

        @field:URL
        val photoURL: String?,

        @field:NotNull
        val hireDate: LocalDate

) {
    constructor() : this(name = "", hireDate = LocalDate.MIN, photoURL = null)
}

interface EmployeeRepository : JpaRepository<Employee, Long>
