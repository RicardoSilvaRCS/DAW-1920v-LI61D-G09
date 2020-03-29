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





    /*fun create( newProject: ProjectsInputModel) {
        /**
         * Moved the algorithm to the ProjectsInputModel class. To test this:
         *
         * 1º- Make instance of ProjectsInputModel w/ the transitions you wanna test:
         *     val proj: ProjectsInputModel = ProjectsInputModel(
         *                  "someName",
         *                  "some desccr",
         *                  arrayOf("somelabel", "someotherlabel"),
         *                  "initialstate",
         *                  arrayof(your transitions)
         *     )
         *
         * 2º- Call the method verifyTransitions, returns a boolean:
         *      proj.verifyTransitions()
         */
        val testProj1: ProjectsInputModel = ProjectsInputModel(
                "asd",
                "asdas",
                arrayOf("ff","ss"),
                "A",
                arrayOf( Pair("A","B") , Pair("B","A") , Pair("A","C") , Pair("C","closed") , Pair("closed","C") , Pair("closed","archived"))
        )
        val r1 = testProj1.verifyTransitions()

        val testProj2: ProjectsInputModel = ProjectsInputModel(
                "asd",
                "asdas",
                arrayOf("ff","ss"),
                "A",
                arrayOf( Pair("A","B") , Pair("A","C") , Pair("C","closed") , Pair("closed","C") , Pair("closed","archived"))
        )
        val r2 = testProj2.verifyTransitions()

        val testProj3: ProjectsInputModel = ProjectsInputModel(
                "asd",
                "asdas",
                arrayOf("ff","ss"),
                "A",
                arrayOf( Pair("A","B") , Pair("B","D"), Pair("D", "closed") , Pair("A","C") , Pair("A", "E"), Pair("E", "A"), Pair("C","closed") , Pair("closed","C") , Pair("closed", "D"), Pair("closed","archived"))
        )
        val r3 = testProj3.verifyTransitions()

        if(!newProject.verifyTransitions()) {
            /**
             * TODO: Transitions are not valid :)
             */
        }
          return CreateProject.execute(newProject, dbs.connection)
    }*/

}