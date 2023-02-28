package example.project.app

import com.fasterxml.jackson.databind.ObjectMapper
import example.project.app.dtos.UserLoginDTO
import example.project.app.dtos.UserRegisterDTO
import example.project.app.h2.MyTestDriver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.datasource.url=jdbc:h2:mem:testdb;mode=mysql",
        "spring.datasource.username=sa",
        "spring.datasource.password=password",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
    ]
)
@AutoConfigureMockMvc
class AppApplicationTest @Autowired constructor(val mockMvc: MockMvc, val objectMapper: ObjectMapper) {

    private val driver = MyTestDriver()

    @Test
    fun `connection to h2 test db`() {
        val connection = driver.getConnection()
        assertThat(connection).isNotNull
    }

    @Test
    fun `should register new user`() {

        val newUser = UserRegisterDTO("NewUser", "testjunit1@user.com", "123456")

        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newUser)
        }
            .andDo { print() }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }

    @Test
    fun `should register and be able to login and get jwt cookie`() {

        val email = "login@test.com"
        val password = "test_password"
        val newUser = UserRegisterDTO(email, email, password)
        val newUserLogin = UserLoginDTO(email, password)

        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newUser)
        }
            .andDo { print() }
            .andExpect { status { isCreated() } }

        mockMvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newUserLogin)
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                cookie {
                    exists("jwt")
                    httpOnly("jwt", true)

                }
            }
    }

    @Test
    fun `cannot access protected endpoint without jwt cookie`() {

        mockMvc.get("/protected")
            .andDo { print() }
            .andExpect { status { isUnauthorized() } }
    }

    @Test
    fun `wrong credentials should return unauthorized`() {

        val wrongCredentials = UserLoginDTO("wrong_email@wrong.com", "wrong_password")

        mockMvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(wrongCredentials)
        }
            .andDo { print() }
            .andExpect { status { isUnauthorized() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }


}