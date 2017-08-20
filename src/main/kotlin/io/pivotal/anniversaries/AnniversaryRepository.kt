package io.pivotal.anniversaries

import org.springframework.data.jpa.repository.JpaRepository


interface AnniversaryRepository: JpaRepository<Anniversary, Long>
