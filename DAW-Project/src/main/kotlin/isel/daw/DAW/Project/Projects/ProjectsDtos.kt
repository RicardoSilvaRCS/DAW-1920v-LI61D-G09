package isel.daw.DAW.Project.Projects

import com.fasterxml.jackson.annotation.JsonCreator

/**
 *
 */
class ProjectsInputModel @JsonCreator constructor(
        val name: String,
        val descr: String,
        val labels: Array<String>,
        val initstate: String,
        val transitions: Array<String>
)

/**
 *
 */
class ProjectsOutputModel(val name: String, val descr: String)

/**
 *
 */
class ProjectsInfoOutputModel(
        val name: String,
        val descr: String,
        val labels: Array<String>,
        val initstate: String,
        val transitions: Array<String>,
        val issuesurl: String
)

/**
 * TODO:
 */
class InvalidProjectException : Exception()