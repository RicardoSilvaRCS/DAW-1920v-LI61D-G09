package isel.daw.DAW.Project.Common

/**
 * This represents an invalid comment exception.
 * Thrown when the comment does not match his structure
 */
class InvalidCommentException (msg : String ): Exception(msg)

/**
 * This represents an invalid issue exception.
 * Thrown when the issue does not match his structure
 */
class InvalidIssueException (msg : String ): Exception(msg)

/**
 * This represents an invalid project exception. * Thrown when the project does not match his structure
 */
class InvalidProjectException (msg : String ): Exception(msg)

/**
 * This represents an invalid state transition exception.
 * Thrown when there is an invalid transition in states
 */
class InvalidStateTransitionException (msg: String): Exception(msg) {
    val DEFAULT_MSG: String = "Invalid state transition:\n"
    override val message: String?
        get() {
            return DEFAULT_MSG.plus(super.message)
        }
}

/**
 * This represents a failure on the insertion of information in db.
 * Thrown when an error occurred on the insertion process
 */
class InsertionFailed (msg: String): Exception(msg)

/**
 * This represents a failure on the update of information in db.
 * Thrown when an error occurred on the update process
 */
class UpdateFailed (msg: String): Exception(msg)
