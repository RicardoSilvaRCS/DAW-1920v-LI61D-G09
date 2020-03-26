package isel.daw.DAW.Project.Projects.ProjectsDto

import com.fasterxml.jackson.annotation.JsonCreator

/**
 *  Data model for the creation of a new Project
 */
class ProjectsInputModel @JsonCreator constructor(
        val name: String,
        val descr: String,
        val labels: Array<String>,
        val initstate: String,
        val transitions: Array<Pair<String, String>>
)

/**
 *  Data model for the representation of a projects info to update
 */
class ProjectsUpdateInputModel @JsonCreator constructor(val newDescr: String)

/**
 *  Data model for the representation of a Project
 */
class ProjectsOutputModel(val name: String, val descr: String)

/**
 * Data model for the full detailed representation of a Project
 */
class ProjectsInfoOutputModel(
        var name: String = "",
        var descr: String? = "",
        var labels: MutableList<String> = arrayListOf(),
        var initstate: String? = "",
        var states: MutableList<String> = arrayListOf(),
        var transitions: MutableList<Pair<String, String>> = arrayListOf()
)

