package isel.daw.DAW.Project.Model.Projects

import org.springframework.stereotype.Component

@Component
class Projects () {

    /**
     * Function responsible for returning all existing projects.
     */
    fun getProjects(): List<ProjectIdentifierDto> {
        //TODO: Make to request to the DB, receive the information and return a List of objects that have the name and description of each project existing
        throw NotImplementedError()
    }
}