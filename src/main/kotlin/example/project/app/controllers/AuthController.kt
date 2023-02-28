package example.project.app.controllers

import example.project.app.dtos.*
import example.project.app.models.User
import example.project.app.responses.LoginResponse
import example.project.app.responses.StandardResponse
import example.project.app.services.AuthService
import example.project.app.services.interfaces.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/auth")
class AuthController(private val authService: AuthService) {

    @Autowired
    lateinit var userService: IUserService

   @GetMapping("test")
    fun test(): ResponseEntity<StandardResponse> {
        return ResponseEntity.ok(StandardResponse(true, "Hello from test route"))
    }

    @PostMapping("register")
    fun register(@RequestBody body: UserRegisterDTO): ResponseEntity<User> {

        return authService.register(body)
    }

    @PostMapping("login")
    fun login(@RequestBody body: UserLoginDTO, response: HttpServletResponse): ResponseEntity<LoginResponse> {
        return authService.login(body, response)
    }

    @PostMapping("logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<StandardResponse> {
        return authService.logout(request, response)
    }

    @DeleteMapping("users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable userId: UUID) {
        return userService.delete(userId)
    }

}