package br.com.jeffersonmarques.restapi.repository

import br.com.jeffersonmarques.restapi.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByNameContainingIgnoreCase(pageable: Pageable, name: String): Page<User>
    fun findByEmailLikeIgnoreCase(email: String): Optional<User>
}