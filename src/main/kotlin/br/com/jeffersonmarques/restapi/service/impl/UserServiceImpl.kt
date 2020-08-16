package br.com.jeffersonmarques.restapi.service.impl

import br.com.jeffersonmarques.restapi.dto.UserDTO
import br.com.jeffersonmarques.restapi.error.exception.ExistingEmailException
import br.com.jeffersonmarques.restapi.error.exception.UserNotFoundException
import br.com.jeffersonmarques.restapi.model.User
import br.com.jeffersonmarques.restapi.repository.UserRepository
import br.com.jeffersonmarques.restapi.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.Clock

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val clock: Clock) : UserService {

    override fun save(userDTO: UserDTO): User {
        checksIfTheEmailExists(userDTO.email!!)
        return userRepository.save(userDTO.transformToUser(clock))
    }

    override fun findAll(pageable: Pageable): Page<User> {
        return userRepository.findAll(pageable)
    }

    override fun findByName(pageable: Pageable, name: String): Page<User> {
        return userRepository.findByNameContainingIgnoreCase(pageable, name)
    }

    override fun update(userDTO: UserDTO, id: Long): User {
        var actualUser: User = findById(id)

        if (actualUser.email != userDTO.email) {
            checksIfTheEmailExists(userDTO.email!!)
        }

        val modifieldUser = actualUser.copy(name = userDTO.name, lastName = userDTO.lastName, email = userDTO.email, age = userDTO.age)

        return userRepository.save(modifieldUser)
    }

    override fun delete(id: Long) {
        userRepository.delete(findById(id))
    }

    override fun findById(id: Long): User
            = userRepository.findById(id).orElseThrow { throw UserNotFoundException() }

    private fun checksIfTheEmailExists(email: String)
            = userRepository.findByEmailLikeIgnoreCase(email).ifPresent { throw ExistingEmailException() }
}