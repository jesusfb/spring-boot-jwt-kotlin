package example.project.app.services.interfaces

import example.project.app.models.User
import io.jsonwebtoken.Claims

interface IJwtService {
    fun generate(user: User): String
    fun getSubject(token: String): String
    fun getClaims(token: String): Claims
}