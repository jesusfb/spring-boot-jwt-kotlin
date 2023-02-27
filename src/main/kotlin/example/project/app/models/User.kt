package example.project.app.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.Date
import java.util.UUID
import javax.persistence.*

@Entity
class User {

    @Id
    @GeneratedValue()
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    var id: UUID? = null


    @Column
    var username: String = ""

    @Column(unique = true)
    var email: String = ""

    @Column
    var password: String = ""
        @JsonIgnore
        get
        set(value){
            val encoder = BCryptPasswordEncoder()
            field = encoder.encode(value)
        }

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt = Date()
}