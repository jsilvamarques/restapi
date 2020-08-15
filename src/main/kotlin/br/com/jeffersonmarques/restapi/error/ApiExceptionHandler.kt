package br.com.jeffersonmarques.restapi.error

import br.com.jeffersonmarques.restapi.error.exception.ExistingEmailException
import br.com.jeffersonmarques.restapi.error.exception.UserNotFoundException
import br.com.jeffersonmarques.restapi.utils.NOT_AVAILABLE_MESSAGE
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class ApiExceptionHandler(private val messageSource: MessageSource) {

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handle(
            methodArgumentNotValidException: MethodArgumentNotValidException,
            locale: Locale
    ) : ResponseEntity<ApiError> {
        val errorCode: String = methodArgumentNotValidException
                .bindingResult
                .allErrors
                .stream()
                .map(ObjectError::getDefaultMessage)
                .findFirst()
                .get()

        return ResponseEntity.badRequest().body(buildToApiError(errorCode, locale))
    }

    @ExceptionHandler(value = [UserNotFoundException::class])
    fun handleUserNotFoundException(
            userNotFoundException: UserNotFoundException,
            locale: Locale
    ): ResponseEntity<ApiError>
            = ResponseEntity.badRequest().body(buildToApiError(userNotFoundException.errorCode, locale))

    @ExceptionHandler(value = [ExistingEmailException::class])
    fun handleExistingEmailException(
            existingEmailException: ExistingEmailException,
            locale: Locale
    ): ResponseEntity<ApiError>
            = ResponseEntity.badRequest().body(buildToApiError(existingEmailException.errorCode, locale))

    fun buildToApiError(errorCode: String, locale: Locale, vararg args: String): ApiError {
        var errorMessage = try {
            messageSource.getMessage(errorCode, args, locale)
        }
        catch (ex: NoSuchMessageException) {
            NOT_AVAILABLE_MESSAGE
        }

        return ApiError(errorCode, errorMessage)
    }
}