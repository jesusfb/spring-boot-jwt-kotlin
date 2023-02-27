package example.project.app

import example.project.app.services.interfaces.IJwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter: OncePerRequestFilter() {

    @Autowired
    lateinit var jwtService: IJwtService

    fun getJwt(request: HttpServletRequest): String? {
        var tokenValue: String? = null
        request.cookies?.forEach {
            if (it.name == "jwt") {
                tokenValue = it.value
            }
        }
        return tokenValue
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenValue = getJwt(request)
        if (tokenValue.isNullOrBlank()) {
            return filterChain.doFilter(request, response)
        }

        val context = SecurityContextHolder.getContext()

        if (context.authentication == null || context.authentication is AnonymousAuthenticationToken)
        {
            return try {
                val userId = jwtService.getSubject(tokenValue)
                context.authentication = UsernamePasswordAuthenticationToken(userId, null, listOf(
                    GrantedAuthority { "ROLE_USER" }))
                SecurityContextHolder.setContext(context)
                filterChain.doFilter(request, response)
            } catch (e: Exception){
                filterChain.doFilter(request, response)
            }
        }

        return filterChain.doFilter(request, response)
    }
}