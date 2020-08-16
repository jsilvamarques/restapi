package br.com.jeffersonmarques.restapi.service

import br.com.jeffersonmarques.restapi.dto.UserDTO
import br.com.jeffersonmarques.restapi.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {
    fun save(userDTO: UserDTO): User
    fun findById(id: Long): User
    fun findByName(pageable: Pageable, name: String): Page<User>
    fun update(userDTO: UserDTO, id: Long): User
    fun delete(id: Long)
    fun findAll(pageable: Pageable): Page<User>

}