package isel.daw.DAW.Project.Projects

import isel.daw.DAW.Project.Common.InvalidProjectException
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInfoOutputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsOutputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsUpdateInputModel
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Services layer for Projects related operations. All the business logic is implemented in this layer.
 *
 * TODO: We need to make our validations.
 */


@Service
@Component
class ProjectsServices(private val projectsrepo: ProjectsRepository) {

    fun getProjects(): List<ProjectsOutputModel> {
        return projectsrepo.getAll()
    }

    fun getProject( name: String ): ProjectsInfoOutputModel {
        return projectsrepo.getByName(name)
    }

    fun createProject(newProject : ProjectsInputModel) {
        if(!newProject.name.isNullOrEmpty() && !newProject.name.isBlank()&& !newProject.initstate.isNullOrEmpty() && newProject.verifyTransitions()){
            return projectsrepo.create(newProject)
        }else{
            throw InvalidProjectException("Invalid information received for the creation of a project.")
        }
    }

    fun updateProject( name: String, newProj: ProjectsUpdateInputModel) {
        return projectsrepo.update(name, newProj)
    }

    fun deleteProject( name: String ) {
        return projectsrepo.delete(name)
    }
}