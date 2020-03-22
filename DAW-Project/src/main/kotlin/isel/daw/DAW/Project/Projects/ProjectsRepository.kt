package isel.daw.DAW.Project.Projects

import isel.daw.DAW.Project.Common.*
import isel.daw.DAW.Project.Projects.ProjectsDal.GetProject
import isel.daw.DAW.Project.Projects.ProjectsDal.GetProjects
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInfoOutputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsOutputModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import java.io.Console
import javax.sql.DataSource

/**
 * Repository for acessing Projects related data
 */
@Repository
@Component
class ProjectsRepository(@Autowired val dbs: DataSource) {

    fun getAll(): List<ProjectsOutputModel> {
        return GetProjects.execute(dbs.connection)
    }

    @GetMapping(GET_SINGLE_PROJECT_PATH)
    fun getByName( name: String ): ProjectsInfoOutputModel {
        return GetProject.execute(name, dbs.connection)
    }

    @PostMapping(CREATE_PROJECT_PATH)
    fun create( newProject: ProjectsInputModel) {
        print(newProject)
        //throw NotImplementedError()
    }

    @PutMapping(UPDATE_PROJECT_PATH)
    fun update( description: String ) {
        throw NotImplementedError()
    }

    @DeleteMapping(DELETE_PROJECT_PATH)
    fun delete( name: String ) {
        throw NotImplementedError()
    }
}