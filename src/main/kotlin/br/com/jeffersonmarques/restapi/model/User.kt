package br.com.jeffersonmarques.restapi.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "tb_user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    val id: Long? = null,
    @Column(nullable = false)
    @JsonProperty("name")
    val name: String? = null,
    @Column(name = "last_name", nullable = false)
    @JsonProperty("last_name")
    val lastName: String? = null,
    @Column(nullable = false, unique = true)
    @JsonProperty("email")
    val email: String? = null,
    @Column(nullable = false)
    @JsonProperty("age")
    val age: Int? = null,
    @Column(name ="created_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("created_date")
    val createdDate: LocalDate? = null
) : Serializable {
    companion object {
        private const val serialVersionUID = -19L
    }
}