package isel.daw.DAW.Project.Projects

import isel.daw.DAW.Project.Common.*
import isel.daw.DAW.Project.Projects.ProjectsDal.GetProject
import isel.daw.DAW.Project.Projects.ProjectsDal.GetProjects
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInfoOutputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsOutputModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.sql.Connection
import javax.sql.DataSource

/**
 * Controller for Projects resources
 */
@RestController
@RequestMapping(PROJECTS_PATH)
class ProjectsController(val projectsrepo: ProjectsRepository) {

    /**
     * Endpoint to obtain all existing projects. Might receive filtering parameters(creation-date, issue-number, etc.).
     * GET /projects/getprojects
     */
    @GetMapping(GET_PROJECTS_PATH)
    fun getProjects (): List<ProjectsOutputModel> {
       return projectsrepo.getAll()
    }

    /**
     * Endpoint to obtain detailed information of a single project. Must receive the @param name:String that identifies the project.
     * GET /projects/getproject name={ProjName}
     */
    @GetMapping(GET_SINGLE_PROJECT_PATH)
    fun getProject( name: String ): ProjectsInfoOutputModel {
        return projectsrepo.getByName(name)
    }

    /**
     * Endpoint to create a new project. Must receive the following @params:
     *  name:String, the name of the project;
     *  description:String, a short description;
     *  labels:List<String>, the labels used in the projects context;
     *  initstate:String, initial state used in the projects context;
     *  transitions:List<String>, possible state transitions.
     * POST /projects/createproject name={ProjName}&description={projDescr}&labels={onelabel}&labels={twolabel}&initstate={initialState}&transitions={onestate~otherstate}
     */
    @PostMapping(CREATE_PROJECT_PATH, consumes = ["application/json"])
    fun createProject( @RequestBody newProject: ProjectsInputModel ) {
        return projectsrepo.create(newProject)
    }

    /**
     * Endpoint to update a projects information. For now only receives a new @param description:String.
     * PUT /projects/updateproject description={newProjDescr}
     */
    @PutMapping(UPDATE_PROJECT_PATH)
    fun updateProject( description: String ) {
        return projectsrepo.update(description)
    }

    /**
     * Endpoint to delete a project. Must receive the @param name:String of the project.
     * DELETE /projects/deleteproject name={projName}
     */
    @DeleteMapping(DELETE_PROJECT_PATH)
    fun deleteProject( name: String ) {
        return projectsrepo.delete(name)
    }
}