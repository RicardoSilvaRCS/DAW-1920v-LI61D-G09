package isel.daw.DAW.Project.Issues

import com.fasterxml.jackson.annotation.JsonCreator

/**
 *
 */
class IssuesInputModel @JsonCreator constructor(
        val name: String,
        val projname: String,
        val descr: String,
        val labels: Array<String>
)

/**
 *
 */
class IssuesOutputModel(val name: String, val descr: String, val state: String)

/**
 *
 */
class IssuesInfoOutputModel(
        val name: String,
        val projname: String,
        val descr: String,
        val labels: Array<String>,
        val state: String,
        val commentssurl: String
)

/**
 * TODO:
 */
class InvalidIssuesException : Exception()