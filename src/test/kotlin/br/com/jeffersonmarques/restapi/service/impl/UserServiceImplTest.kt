package br.com.jeffersonmarques.restapi.service.impl

import br.com.jeffersonmarques.restapi.RestPageImpl
import br.com.jeffersonmarques.restapi.StreamUtils
import br.com.jeffersonmarques.restapi.dto.UserDTO
import br.com.jeffersonmarques.restapi.error.exception.ExistingEmailException
import br.com.jeffersonmarques.restapi.error.exception.UserNotFoundException
import br.com.jeffersonmarques.restapi.model.User
import br.com.jeffersonmarques.restapi.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

@RunWith(MockitoJUnitRunner::class)
class UserServiceImplTest {

    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var clock: Clock

    private lateinit var objectMapper: ObjectMapper
    private lateinit var streamUtils: StreamUtils
    private lateinit var userServiceImpl: UserServiceImpl
    private lateinit var pageable: Pageable

    @Before
    fun setup() {
        objectMapper = ObjectMapper().registerModule(JavaTimeModule())
        streamUtils = StreamUtils()
        clock = Clock.fixed(Instant.parse("2020-08-13T12:00:00Z"), ZoneId.of("America/Sao_Paulo"))
        userServiceImpl = UserServiceImpl(userRepository, clock)
        pageable = PageRequest.of(1, 10)
    }

    @Test
    fun `save new user`() {
        val expectedNewUser = objectMapper
                .readValue(streamUtils.inputStream(javaClass, "expected_new_user.json"), User::class.java)
        val userDTORequest = UserDTO(
                name = "Marcos",
                lastName = "Mendes",
                email = "marcos.mendes2@gmail.com",
                age = 50
        )

        Mockito.`when`(userRepository.findByEmailLikeIgnoreCase(userDTORequest.email!!)).thenReturn(Optional.empty())
        Mockito.`when`(userRepository.save(userDTORequest.transformToUser(clock))).thenReturn(expectedNewUser)

        val actualNewUser = userServiceImpl.save(userDTORequest)

        Assert.assertEquals(expectedNewUser, actualNewUser)
    }

    @Test
    fun `find user by name`() {

        val expectedUsersWithEqualsNames = objectMapper
                .readValue(streamUtils.inputStream(javaClass, "expected_users_with_equals_names.json"),
                        RestPageImpl::class.java) as RestPageImpl<User>

        val nameRequestParam = "Marcos"

        Mockito.`when`(userRepository.findByNameContainingIgnoreCase(pageable, nameRequestParam))
                .thenReturn(expectedUsersWithEqualsNames)

        val actualUsersWithEqualsNames = userServiceImpl.findByName(pageable, nameRequestParam)

        Assert.assertEquals(expectedUsersWithEqualsNames, actualUsersWithEqualsNames)
    }

    @Test
    fun `update user`() {
        val expectedUpdatedUser = objectMapper
                .readValue(streamUtils.inputStream(javaClass, "expected_updated_user.json"), User::class.java)
        val updateUserDTORequest = UserDTO(
                name = "Geraldo",
                lastName = "Garcia",
                email = "garcia2@gmail.com",
                age = 56
        )
        val idRequestVariable = 150L
        val expectedUpdatedUserOptional = Optional.of(expectedUpdatedUser)

        Mockito.`when`(userRepository.findById(idRequestVariable)).thenReturn(expectedUpdatedUserOptional)
        Mockito.`when`(userRepository.findByEmailLikeIgnoreCase(updateUserDTORequest.email!!))
                .thenReturn(Optional.empty())
        Mockito.`when`(userRepository.save(Mockito.any(User::class.java))).thenReturn(expectedUpdatedUser)

        val actualUpdatedUser = userServiceImpl.update(updateUserDTORequest, idRequestVariable)

        Assert.assertEquals(expectedUpdatedUser, actualUpdatedUser)
    }

    @Test
    fun `delete user`() {
        val expectedDeletedUser = objectMapper
                .readValue(streamUtils.inputStream(javaClass, "expected_deleted_user.json"), User::class.java)
        val idRequestVariable = 2L
        val expectedUpdatedUserOptional = Optional.of(expectedDeletedUser)

        Mockito.`when`(userRepository.findById(idRequestVariable)).thenReturn(expectedUpdatedUserOptional)

        userServiceImpl.delete(idRequestVariable)
    }

    @Test(expected = UserNotFoundException::class)
    fun `return UserNotFoundException if user not found`() {
        val idRequestVariable = 2L

        Mockito.`when`(userRepository.findById(idRequestVariable)).thenReturn(Optional.empty())

        userServiceImpl.delete(idRequestVariable)
    }

    @Test(expected = ExistingEmailException::class)
    fun `return ExistingEmailException if user email is existing`() {
        val expectedUpdatedUser = objectMapper
                .readValue(streamUtils.inputStream(javaClass, "expected_updated_user.json"), User::class.java)
        val updateUserDTORequest = UserDTO(
                name = "Geraldo",
                lastName = "Garcia",
                email = "garcia2@gmail.com",
                age = 56
        )
        val idRequestVariable = 150L
        val expectedUpdatedUserOptional = Optional.of(expectedUpdatedUser)

        Mockito.`when`(userRepository.findById(idRequestVariable)).thenReturn(expectedUpdatedUserOptional)
        Mockito.`when`(userRepository.findByEmailLikeIgnoreCase(updateUserDTORequest.email!!))
                .thenReturn(expectedUpdatedUserOptional)

        userServiceImpl.update(updateUserDTORequest, idRequestVariable)
    }
}