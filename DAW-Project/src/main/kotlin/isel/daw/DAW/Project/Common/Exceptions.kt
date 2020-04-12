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
 * Thrown when the comment received does not match his structure
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
 * Thrown when the issue received does not match his structure
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
 * This represents an invalid issue label value exception.
 * Thrown if during the creation/update of an issue, at least one of the labels received has an invalid value,
 *  and by invalid it means, it's non existent in the projects context.
 */
class InvalidIssueLabelException (val details: String) : ApiException(details) {
    override val type: String
        get() = "/issues/problems/invalid-label"
    override val title: String
        get() = "Invalid Issue Label"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.BAD_REQUEST
}

/**
 * This represents an invalid project exception.
 * Thrown when the project received does not match his structure
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
 * This represents an illegal name for a project.
 * Thrown when the user tries to create a project with the same name as an existent one.
 */
class ProjectNameConflictException (val details: String): ApiException(details) {
    override val type: String
        get() = "/projects/problems/name-conflict"
    override val title: String
        get() = "Project Name Conflict"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.CONFLICT
}

/**
 * This represents an invalid resource requested exception.
 * Thrown when the user tries to perform an action over a resource that doesn't exist.
 */
class InvalidResourceRequestedException (val details: String): ApiException(details) {
    override val type: String
        get() = "/api/problems/invalid-resource-requested"
    override val title: String
        get() = "Invalid Resource Requested"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.BAD_REQUEST
}

/**
 * This represents an internal procedure exception.
 * Thrown when an internal procedure can't be completed because of external reasons.
 */
class InternalProcedureException (val details: String): ApiException(details) {
    override val type: String
        get() = "/api/problems/internal-procedure-failed"
    override val title: String
        get() = "Internal Procedure Failed"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.INTERNAL_SERVER_ERROR

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