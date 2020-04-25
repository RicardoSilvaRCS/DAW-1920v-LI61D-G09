package isel.daw.DAW.Project.Projects

import isel.daw.DAW.Project.Common.InvalidProjectException
import isel.daw.DAW.Project.Common.InvalidResourceRequestedException
import isel.daw.DAW.Project.Common.ProjectNameConflictException
import isel.daw.DAW.Project.Projects.ProjectsDto.*
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Services layer for Projects related operations. All the business logic is implemented in this layer.
 */


@Service
@Component
class ProjectsServices(private val projectsrepo: ProjectsRepository) {

    fun getProjects(userName : String): List<ProjectsOutputModel> {
        return projectsrepo.getAll(userName)
    }

    fun getProject( name: String ): ProjectsInfoOutputModel {
        if(name.isBlank() || name.isEmpty()) {
            throw InvalidResourceRequestedException("No project name received.")
        }
        return projectsrepo.getByName(name)
    }

    fun createProject(newProject : ProjectsInputModel , userName: String): ProjectCreationResponse {
        if(newProject.isValid()) {
            if(projectsrepo.getByName(newProject.name).name.isNotEmpty()) {
                throw ProjectNameConflictException("A project with the name '${newProject.name}' already exists.")
            }
            return projectsrepo.create(newProject,userName)
        } else {
            throw InvalidProjectException("Invalid information received for the creation of a project.")
        }
    }

    fun updateProject(name: String, newProj: ProjectsUpdateInputModel): ProjectUpdatedResponse {
        if(projectsrepo.getByName(name).name.isEmpty()) {
            throw InvalidResourceRequestedException("There is no project with the name '$name' in the system.")
        }
        return projectsrepo.update(name, newProj)
    }

    fun deleteProject( name: String ): ProjectDeletedResponse {
        if(name.isEmpty() || name.isBlank()) {
            throw InvalidResourceRequestedException("No project name was sent.")
        }
        return projectsrepo.delete(name)
    }

    fun postProjectLabel(projName : String, labelName : String): ProjectLabelCreationResponse{
        return projectsrepo.createLabel(projName, labelName)
    }

    fun deleteProjectLabel(projName : String, labelName : String): ProjectLabelDeleteResponse{
        return projectsrepo.deleteLabel(projName, labelName)
    }
}