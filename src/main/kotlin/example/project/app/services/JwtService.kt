package example.project.app.services

import example.project.app.config.JwtConfigProperties
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import example.project.app.models.User
import example.project.app.services.interfaces.IJwtService
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date

@Service
class JwtService(
    private val jwtConfigProperties: JwtConfigProperties
): IJwtService {
    private val secretKey: Key = Keys.hmacShaKeyFor(jwtConfigProperties.secret.toByteArray())
    var expiresInMs: Int = jwtConfigProperties.expiresInMs

    override fun generate(user: User): String {
        val claims : HashMap<String, Any?> = HashMap()
        claims["email"] = user.email
        claims["sub"] = user.id.toString()

        return Jwts.builder()
            .setClaims(claims)
            .setIssuer(jwtConfigProperties.issuerURL)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + jwtConfigProperties.expiresInMs))
            .signWith(secretKey)
            .compact()
    }

    override fun getSubject(token: String): String {
        return getClaims(token).subject
    }

    override fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
    }
}