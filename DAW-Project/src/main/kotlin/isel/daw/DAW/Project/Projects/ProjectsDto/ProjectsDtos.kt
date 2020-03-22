package isel.daw.DAW.Project.Projects.ProjectsDto

import com.fasterxml.jackson.annotation.JsonCreator

/**
 *
 */
class ProjectsInputModel @JsonCreator constructor(
        val name: String,
        val descr: String,
        val labels: Array<String>,
        val initstate: String,
        val transitions: Array<Pair<String, String>>
)

/**
 *
 */
class ProjectsOutputModel(val name: String, val descr: String)

/**
 *
 */
class ProjectsInfoOutputModel(
        var name: String = "",
        var descr: String? = "",
        var labels: MutableList<String> = arrayListOf(),
        var initstate: String? = "",
        var states: MutableList<String> = arrayListOf(),
        var transitions: MutableList<Pair<String, String>> = arrayListOf()
)

