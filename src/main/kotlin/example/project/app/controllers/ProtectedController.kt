package example.project.app.controllers

import example.project.app.models.User
import example.project.app.responses.StandardResponse
import example.project.app.services.interfaces.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("protected")
class ProtectedController() {

    @Autowired
    lateinit var userService: IUserService

    @GetMapping
    fun helloFromProtected(): ResponseEntity<StandardResponse>{
        return ResponseEntity.ok(StandardResponse(true, "Hello from protected route"))
    }

    @GetMapping("info")
    fun info(): ResponseEntity<User>{
        val id = SecurityContextHolder.getContext().authentication.principal.toString()
        return ResponseEntity.ok(userService.getUserById(id))
    }
}