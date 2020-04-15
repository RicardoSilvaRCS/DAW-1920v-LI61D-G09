package isel.daw.DAW.Project.Projects

import isel.daw.DAW.Project.Common.*
import isel.daw.DAW.Project.Projects.ProjectsDto.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Controller for Projects resources
 */
@RestController
class ProjectsController(private val projectservices: ProjectsServices) {

    /**
     * Endpoint to obtain all existing projects. Might receive filtering parameters(creation-date, issue-number, etc.).
     * GET /projects
     */
    @GetMapping(GET_PROJECTS_PATH)
    fun getProjects (): List<SirenEntity<ProjectsOutputModel>>{
       val projects: MutableList<SirenEntity<ProjectsOutputModel>> = mutableListOf()
        projectservices.getProjects().forEach{
            projects.add(it.toSirenObject())
        }
        return projects
    }

    /**
     * Endpoint to obtain detailed information of a single project. Must receive the @param name:String that identifies the project.
     * GET /projects/{pname}
     */
    @GetMapping(GET_SINGLE_PROJECT_PATH)
    fun getProject(@PathVariable pname: String): SirenEntity<ProjectsInfoOutputModel> {
        return projectservices.getProject(pname).toSirenObject()
    }

    /**
     * Endpoint to create a new project. Must receive the following:
     *  name:String, the name of the project;
     *  description:String, a short description;
     *  labels:List<String>, the labels used in the projects context;
     *  initstate:String, initial state used in the projects context;
     *  transitions:Array<Pair<String,String>>, possible state transitions.
     * POST /projects
     */
    @PostMapping(CREATE_PROJECT_PATH, consumes = ["application/json"])
    fun createProject( @RequestBody newProject: ProjectsInputModel ): SirenEntity<ProjectCreationResponse> {
        return projectservices.createProject(newProject).toSirenObject()
    }

    /**
    * Endpoint to update a projects information.
    * PUT /projects/{pname}
    */
    @PutMapping(UPDATE_PROJECT_PATH, consumes = ["application/json"])
    fun updateProject(@PathVariable pname: String,  @RequestBody newProj: ProjectsUpdateInputModel  ): SirenEntity<ProjectUpdatedResponse> {
        return projectservices.updateProject(pname, newProj).toSirenObject()
    }

    /**
     * Endpoint to delete a project.
     * DELETE /projects/{pname}
     */
    @DeleteMapping(DELETE_PROJECT_PATH)
    fun deleteProject(@PathVariable pname: String): SirenEntity<ProjectDeletedResponse> {
        return projectservices.deleteProject(pname).toSirenObject()
    }
}