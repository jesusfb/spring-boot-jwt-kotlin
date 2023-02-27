package example.project.app

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import example.project.app.dtos.UserLoginDTO
import example.project.app.dtos.UserRegisterDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class AppApplicationTest @Autowired constructor(val mockMvc: MockMvc , val objectMapper: ObjectMapper) {


    @Test
    fun generateSecret() {
        for (range in 1..10) {
            val key = Keys.secretKeyFor(SignatureAlgorithm.HS512)
            println(Encoders.BASE64.encode(key.encoded))
        }
    }


    @Test
    fun `should register new user`() {
        // given
        val newUser = UserRegisterDTO("NewUser", "newuser@user.com", "123456")

        //when
        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newUser)
        }

            //then
            .andDo { print() }
            .andExpect { status { isCreated() } }
    }

    @Test
    fun `should return wrong credentials`() {
        // given
        val wrongCredentials = UserLoginDTO("wrong_email@wrong.com", "wrong_password")

        //when
        mockMvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(wrongCredentials)
        }

        //then
            .andDo { print() }
            .andExpect { status { isUnauthorized() } }
    }
}