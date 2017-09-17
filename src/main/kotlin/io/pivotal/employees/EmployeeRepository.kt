package io.pivotal.employees

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (id != other.id) return false
        if (name != other.name) return false
        if (hireDate != other.hireDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + hireDate.hashCode()
        return result
    }
}

interface EmployeeRepository : JpaRepository<Employee, Long>
