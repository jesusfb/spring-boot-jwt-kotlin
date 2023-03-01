package example.project.app.config

import example.project.app.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationProvider: AuthenticationProvider,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authEntryPoint: AuthenticationEntryPoint
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors {}
            .csrf { it ->
                it.disable()
            .authorizeRequests{
                it.antMatchers("/api/**").permitAll()
                it.anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }}
            .exceptionHandling {
                it.authenticationEntryPoint(authEntryPoint)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}