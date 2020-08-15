package br.com.jeffersonmarques.restapi.repository

import br.com.jeffersonmarques.restapi.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.ArrayList

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByNameLikeIgnoreCase(name: String): ArrayList<User>
    fun findByEmailLikeIgnoreCase(email: String): Optional<User>
}