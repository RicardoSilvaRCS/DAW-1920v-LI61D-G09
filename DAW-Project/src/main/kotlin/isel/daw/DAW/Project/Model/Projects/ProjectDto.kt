package isel.daw.DAW.Project.Model.Projects

/**
 *  Data class that represents a Project. The type of the attributes will probably suffer changes.
 */

data class ProjectDto (
        val name: String,
        val description: String,
        val labels: List<String>,
        val initialState: String,
        val states: List<String>,
        val transitions: List<String>
)