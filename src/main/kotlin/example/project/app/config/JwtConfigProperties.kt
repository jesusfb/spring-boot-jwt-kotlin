package example.project.app.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
data class JwtConfigProperties(
   var secret: String = "my-super-secret-key-please-change-me-in-production",
   var expiresInMs: Int = 1000 * 60 * 30,
   var issuerURL: String = "example.com"
)