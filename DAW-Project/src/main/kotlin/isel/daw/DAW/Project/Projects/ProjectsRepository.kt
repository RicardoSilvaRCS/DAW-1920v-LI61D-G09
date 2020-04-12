package isel.daw.DAW.Project.Projects

import isel.daw.DAW.Project.Projects.ProjectsDal.*
import isel.daw.DAW.Project.Projects.ProjectsDto.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.sql.DataSource

/**
 * Repository for accessing Projects related data
 */

@Repository
@Component
class ProjectsRepository(@Autowired val dbs: DataSource) {

    fun getAll(): List<ProjectsOutputModel> {
        return GetProjects.execute(dbs.connection)
    }

    fun getByName( name: String ): ProjectsInfoOutputModel {
        return GetProject.execute(name, dbs.connection)
    }

    fun create (newProject : ProjectsInputModel ) {
        return CreateProject.execute(newProject, dbs.connection)
    }

    fun update( name: String, newProj: ProjectsUpdateInputModel ) {
        return UpdateProject.execute(name, newProj, dbs.connection)
    }

    fun delete( name: String ) {
        return DeleteProject.execute(name, dbs.connection)
    }

}