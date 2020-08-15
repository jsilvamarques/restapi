package br.com.jeffersonmarques.restapi.service

import br.com.jeffersonmarques.restapi.dto.UserDTO
import br.com.jeffersonmarques.restapi.model.User
import java.util.*
import kotlin.collections.ArrayList

interface UserService {
    fun save(userDTO: UserDTO): User
    fun findById(id: Long): User
    fun findByName(name: String): ArrayList<User>
    fun update(userDTO: UserDTO, id: Long): User
    fun delete(id: Long)
    fun findAll(): MutableList<User>

}