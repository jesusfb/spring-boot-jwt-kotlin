package example.project.app

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class MyAuthEntryPoint(
    @Qualifier("handlerExceptionResolver")
    private val handler: HandlerExceptionResolver
    ): AuthenticationEntryPoint
{

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?,
    ) {
        if (request == null || response == null || authException == null) {
            return
        }
        handler.resolveException(request, response, null, authException)
    }
}