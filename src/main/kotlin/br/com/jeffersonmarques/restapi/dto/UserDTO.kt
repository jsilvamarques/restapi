package br.com.jeffersonmarques.restapi.dto

import br.com.jeffersonmarques.restapi.model.User
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.time.Clock
import java.time.LocalDate
import javax.validation.constraints.*

data class UserDTO(
        @JsonProperty("name")
        @field:NotNull(message = "ERROR-01")
        val name: String? = null,
        @JsonProperty("last_name")
        @field:NotNull(message = "ERROR-02")
        val lastName: String? = null,
        @JsonProperty("email")
        @field:NotNull(message = "ERROR-03")
        @field:Email(message = "ERROR-04")
        val email: String? = null,
        @JsonProperty("age")
        @field:NotNull(message = "ERROR-05")
        @field:PositiveOrZero(message = "ERROR-06")
        val age: Int? = null
) : Serializable {
    companion object {
        private const val serialVersionUID = -26L
    }

    fun transformToUser(clock: Clock): User {
        return User(
                name = this.name,
                lastName = this.lastName,
                email = this.email,
                age = this.age,
                createdDate = LocalDate.now(clock)
        )
    }
}