package isel.daw.DAW.Project.Projects

import isel.daw.DAW.Project.Common.*
import isel.daw.DAW.Project.Projects.ProjectsDal.*
import isel.daw.DAW.Project.Projects.ProjectsDto.*
import isel.daw.DAW.Project.Projects.ProjectsExeceptions.InvalidProjectException
import isel.daw.DAW.Project.Projects.ProjectsExeceptions.InvalidStateTransitionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import java.lang.Error
import java.lang.Exception
import java.util.*
import javax.sql.DataSource

/**
 * Repository for acessing Projects related data
 */

//We need to make here our validations
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
        if(!newProject.name.isNullOrEmpty() && !newProject.name.isBlank()&& !newProject.initstate.isNullOrEmpty() && newProject.verifyTransitions()){
            return CreateProject.execute(newProject, dbs.connection)
        }else{
            throw Exception ("This project is not valid")
        }
    }

    fun update( name: String, newProj: ProjectsUpdateInputModel ) {
        return UpdateProject.execute(name, newProj, dbs.connection)
    }

    fun delete( name: String ) {
        return DeleteProject.execute(name, dbs.connection)
    }

}