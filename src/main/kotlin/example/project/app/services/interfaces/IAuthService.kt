package example.project.app.services.interfaces

import example.project.app.dtos.UserLoginDTO
import example.project.app.dtos.UserRegisterDTO
import example.project.app.models.User
import example.project.app.responses.LoginResponse
import example.project.app.responses.StandardResponse
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface IAuthService {
    fun login(userLoginDTO: UserLoginDTO, response: HttpServletResponse): ResponseEntity<LoginResponse>
    fun register(userRegisterDTO: UserRegisterDTO): ResponseEntity<User>
    fun logout(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<StandardResponse>
}