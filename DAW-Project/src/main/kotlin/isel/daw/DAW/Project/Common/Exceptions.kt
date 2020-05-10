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
 * This represents an illegal issue state value exception.
 * Thrown if during the creation/update of an issue, the state received has an illegal value,
 *  and by illegal it means, it's non existent in the projects context or it's not a real state(empty or blank).
 */
class IllegalIssueStateException (val details: String) : ApiException(details) {
    override val type: String
        get() = "/issues/problems/illegal-state"
    override val title: String
        get() = "Illegal Issue State"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.BAD_REQUEST
}

/**
 * This represents the non-existence of issues in the specified project.
 * Thrown when a request for issues finds no issues.
 */
class IssuesNotFoundException(val details: String): ApiException(details) {
    override val type: String
        get() = "/issues/problems/not-found"
    override val title: String
        get() = "Issues not found"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.NOT_FOUND
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

/**
 * This represents an unauthorized request exception.
 * Thrown when a request is made without proper authentication.
 */
class UnauthorizedException (val details: String): ApiException(details) {
    override val type: String
        get() = "/api/problems/no-auth-header-found"
    override val title: String
        get() = "Request requires authorization"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.UNAUTHORIZED

}

/**
 * This represents a user not found exception.
 * Thrown when a user requested is not found.
 */
class UserNotFoundException (val details: String): ApiException(details) {
    override val type: String
        get() = "/user/problems/user-not-found"
    override val title: String
        get() = "User doesn't exist"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.NOT_FOUND

}

/**
 * This represents invalid user credentials exception.
 * Thrown when a user gives invalid credentials.
 */
class InvalidCredentialsException (val details: String): ApiException(details) {
    override val type: String
        get() = "/user/problems/invalid-credentials"
    override val title: String
        get() = "Invalid Credentials Received"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.UNAUTHORIZED
}


/**
 * This represents a project not found exception.
 * Thrown when the project requested is not found.
 */
class ProjectNotFoundException (val details: String): ApiException(details) {
    override val type: String
        get() = "/projects/problems/project-not-found"
    override val title: String
        get() = "Project not found"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.NOT_FOUND

}


/**
 * This represents a non existing projects response.
 * Thrown when the no projects were found.
 */
class NoProjectsFoundError (val details: String): ApiException(details) {
    override val type: String
        get() = "/projects/problems/projects-not-found"
    override val title: String
        get() = "Projects not found"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.NOT_FOUND

}

/**
 * This represents a non existing user response.
 * Thrown when the no users were found with the specified name.
 */
class NoUsersFoundError (val details: String): ApiException(details) {
    override val type: String
        get() = "/users/problems/users-not-found"
    override val title: String
        get() = "Users not found"
    override val detail: String
        get() = details
    override val status: HttpStatus
        get() = HttpStatus.NOT_FOUND

}