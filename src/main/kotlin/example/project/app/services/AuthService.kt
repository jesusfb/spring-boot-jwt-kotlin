package example.project.app.services

import example.project.app.dtos.*
import example.project.app.exceptions.NoJwtException
import example.project.app.exceptions.UserExistsException
import example.project.app.exceptions.UserNotFoundException
import example.project.app.exceptions.WrongCredentialsException
import example.project.app.models.User
import example.project.app.repositories.UserRepository
import example.project.app.responses.LoginResponse
import example.project.app.responses.StandardResponse
import example.project.app.services.interfaces.IAuthService
import example.project.app.utils.mapper.UserMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class AuthService (
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val mapper: UserMapper,
    private val encoder: BCryptPasswordEncoder): IAuthService {

    private val cookieName = "jwt"

    override fun register(userRegisterDTO: UserRegisterDTO): ResponseEntity<User> {
        val user = mapper.toEntity(userRegisterDTO)
        try {
        return ResponseEntity(userRepository.save(user), HttpStatus.CREATED)
        } catch (e: Exception) {
            throw UserExistsException()
        }
    }

    override fun login(userLoginDTO: UserLoginDTO, response: HttpServletResponse): ResponseEntity<LoginResponse> {
        val user = userRepository.findByEmail(userLoginDTO.email) ?: throw UserNotFoundException()
        val isAuth = encoder.matches(userLoginDTO.password, user.password)
        if (!isAuth) throw WrongCredentialsException()
        val jwt = jwtService.generate(user)
        val cookie = Cookie(cookieName, jwt)
        cookie.apply {
            this.isHttpOnly = true
            this.path = "/"
            this.maxAge = (jwtService.expiresInMs / 1000)
        }
        response.addCookie(cookie)
        return ResponseEntity.ok(LoginResponse(jwt))
    }

    override fun logout(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<StandardResponse> {
        val cookie = request.cookies?.find { cookie -> cookie.name == cookieName } ?: throw NoJwtException()
        cookie.apply {
            path = "/"
            isHttpOnly = true
            value = null
            maxAge = 0
        }
        response.addCookie(cookie)
        return ResponseEntity.ok(StandardResponse(true, "logout successful"))
    }

}