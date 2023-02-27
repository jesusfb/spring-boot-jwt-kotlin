package example.project.app.errorhandlers

import example.project.app.exceptions.NoJwtException
import example.project.app.exceptions.UserExistsException
import example.project.app.exceptions.UserNotFoundException
import example.project.app.exceptions.WrongCredentialsException
import example.project.app.responses.StandardResponse
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class UserErrorHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(NoJwtException::class)
    @ResponseBody
    fun handleNoJwtException(exception: NoJwtException): ResponseEntity<StandardResponse> {
        return ResponseEntity(exception.message?.let { StandardResponse(false, it) }, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(UserNotFoundException::class, WrongCredentialsException::class)
    fun handleWrongCredentialExceptions(request: HttpServletRequest, exception: Exception): ResponseEntity<StandardResponse> {
        return ResponseEntity(StandardResponse(false,"Wrong credentials"), HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(request: HttpServletRequest, exception: IllegalArgumentException): ResponseEntity<StandardResponse> {
        return ResponseEntity(exception.message?.let { StandardResponse(false, it) }, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultDataAccesstException(request: HttpServletRequest, exception: EmptyResultDataAccessException): ResponseEntity<StandardResponse> {
        return ResponseEntity(exception.message?.let { StandardResponse(false, it) }, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UserExistsException::class)
    fun handleUserExistsException(request: HttpServletRequest, exception: UserExistsException): ResponseEntity<StandardResponse> {
        return ResponseEntity(exception.message?.let { StandardResponse(false, it) }, HttpStatus.CONFLICT)
    }
}