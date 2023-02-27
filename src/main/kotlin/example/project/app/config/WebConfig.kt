package example.project.app.config

import example.project.app.repositories.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class WebConfig(private val userRepository: UserRepository) {

    @Bean
    fun createUserDetailService(): UserDetailsService {
       class Service: UserDetailsService {
           override fun loadUserByUsername(username: String): UserDetails {
               val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException("User not found")
               return User(user.email, user.password, listOf(GrantedAuthority { "ROLE_USER" }))
           }
       }
        return Service()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(createUserDetailService())
        authProvider.setPasswordEncoder(createPasswordEncoder())
        return authProvider
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun createPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}