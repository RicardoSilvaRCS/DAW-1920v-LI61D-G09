package isel.daw.DAW.Project.Projects

import isel.daw.DAW.Project.Common.InvalidProjectException
import isel.daw.DAW.Project.Common.InvalidResourceRequestedException
import isel.daw.DAW.Project.Common.ProjectNameConflictException
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInfoOutputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsOutputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsUpdateInputModel
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Services layer for Projects related operations. All the business logic is implemented in this layer.
 */


@Service
@Component
class ProjectsServices(private val projectsrepo: ProjectsRepository) {

    fun getProjects(): List<ProjectsOutputModel> {
        return projectsrepo.getAll()
    }

    fun getProject( name: String ): ProjectsInfoOutputModel {
        if(name.isBlank() || name.isEmpty()) {
            throw InvalidResourceRequestedException("No project name received.")
        }
        return projectsrepo.getByName(name)
    }

    fun createProject(newProject : ProjectsInputModel) {
        if(newProject.isValid()) {
            if(projectsrepo.getByName(newProject.name).name.isNotEmpty()) {
                throw ProjectNameConflictException("A project with the name '${newProject.name}' already exists.")
            }
            return projectsrepo.create(newProject)
        } else {
            throw InvalidProjectException("Invalid information received for the creation of a project.")
        }
    }

    fun updateProject(name: String, newProj: ProjectsUpdateInputModel) {
        if(projectsrepo.getByName(name).name.isEmpty()) {
            throw InvalidResourceRequestedException("There is no project with the name '$name' in the system.")
        }
        return projectsrepo.update(name, newProj)
    }

    fun deleteProject( name: String ) {
        if(name.isEmpty() || name.isBlank()) {
            throw InvalidResourceRequestedException("No project name was sent.")
        }
        return projectsrepo.delete(name)
    }
}