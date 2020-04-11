package isel.daw.DAW.Project.Common

import com.fasterxml.jackson.annotation.JsonInclude
import jdk.net.SocketFlow
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.sql.Timestamp
import java.util.*

/**
 * Base class for the API personalized exceptions.
 * All the API exception must extend this class
 */
abstract class ApiException(details: String) : Exception(details) {
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    abstract val type : String
    abstract val title : String
    abstract val detail : String
    abstract val status : HttpStatus
}

/**
 * This represents an invalid comment exception.
 * Thrown when the comment does not match his structure
 */
class InvalidCommentException (val details: String): ApiException(details) {
    override val type: String
        get() = "/comments/problems/invalid-comment"
    override val title: String
        get() = "Invalid Comment"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.BAD_REQUEST
}

/**
 * This represents an invalid issue exception.
 * Thrown when the issue does not match his structure
 */
class InvalidIssueException (val details: String): ApiException(details) {
    override val type: String
        get() = "/issues/problems/invalid-issue"
    override val title: String
        get() = "Invalid Issue"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.BAD_REQUEST

}

/**
 * This represents an invalid project exception. * Thrown when the project does not match his structure
 */
class InvalidProjectException (val details: String): ApiException(details) {
    override val type: String
        get() = "/projects/problems/invalid-project"
    override val title: String
        get() = "Invalid Project"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.BAD_REQUEST

}

/**
 * This represents an invalid state transition exception.
 * Thrown when there is an invalid transition in states
 */
class InvalidStateTransitionException (val details: String): ApiException(details) {
    override val type: String
        get() = "/projects/problems/invalid-state-transitions"
    override val title: String
        get() = "Invalid State Transitions"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.BAD_REQUEST
}

/**
 * This represents a failure on the insertion of information in db.
 * Thrown when an error occurred on the insertion process
 */
class InsertionFailed (val details: String): ApiException(details) {
    override val type: String
        get() = "/sql/problems/failed-insertion"
    override val title: String
        get() = "Insertion Failed"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.CONFLICT

}

/**
 * This represents a failure on the update of information in db.
 * Thrown when an error occurred on the update process
 */
class UpdateFailed (val details: String): ApiException(details) {
    override val type: String
        get() = "/sql/problems/failed-update"
    override val title: String
        get() = "Update Failed"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.CONFLICT

}

@ControllerAdvice
class APIExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(ApiException::class)
    fun handleException(ex: ApiException) =
            ResponseEntity
                    .status(ex.status)
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                    .body(ProblemJson(
                            timestamp = ex.timestamp,
                            date = ex.date,
                            type = ex.type,
                            title = ex.title,
                            detail = ex.detail,
                            status = ex.status.value()
                    ))
}

@ControllerAdvice
class OtherExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Any> {
        val timestamp =  Timestamp(System.currentTimeMillis())
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(ProblemJson(
                        timestamp =  Timestamp(System.currentTimeMillis()),
                        date = timestamp.toString(),
                        type = "/internal/problems/internal-error",
                        title = "Internal Error",
                        detail = ex.message?: "Internal Error",
                        status = HttpStatus.INTERNAL_SERVER_ERROR.value()
                ))
    }
}

/**
 * Class used for error models, based on the [Problem Json spec](https://tools.ietf.org/html/rfc7807)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProblemJson(
        val timestamp: Timestamp,
        val date: String,
        val type: String,
        val title: String,
        val detail: String,
        val status: Int
)