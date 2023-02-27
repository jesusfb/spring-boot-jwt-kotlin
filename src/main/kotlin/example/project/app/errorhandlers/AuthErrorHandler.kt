package example.project.app.errorhandlers

import example.project.app.responses.StandardResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class AuthErrorHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(AuthenticationException::class)
    @ResponseBody
    fun handleCustomJwtException(exception: Exception): ResponseEntity<StandardResponse> {
        return ResponseEntity(exception.message?.let { StandardResponse(false, it) }, HttpStatus.UNAUTHORIZED)
    }
}