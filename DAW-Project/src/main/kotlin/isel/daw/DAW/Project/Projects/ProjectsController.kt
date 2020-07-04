package isel.daw.DAW.Project.Projects

import isel.daw.DAW.Project.Common.*
import isel.daw.DAW.Project.Projects.ProjectsDto.*
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
    @AuthRequired
    @GetMapping(GET_PROJECTS_PATH)
    fun getProjects (@PathVariable userName : String): List<SirenEntity<ProjectsOutputModel>>{
       val projects: MutableList<SirenEntity<ProjectsOutputModel>> = mutableListOf()
        projectservices.getProjects(userName).forEach{
            projects.add(it.toSirenObject())
        }
        return projects
    }

    /**
     * Endpoint to obtain detailed information of a single project. Must receive the @param name:String that identifies the project.
     * GET /projects/{pname}
     */
    @AuthRequired
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
    @AuthRequired
    @PostMapping(CREATE_PROJECT_PATH, consumes = ["application/json"])
    fun createProject( @RequestBody newProject: ProjectsInputModel, @PathVariable userName: String ): SirenEntity<ProjectCreationResponse> {
        return projectservices.createProject(newProject,userName).toSirenObject()
    }

    /**
    * Endpoint to update a projects information.
    * PUT /projects/{pname}
    */
    @AuthRequired
    @PutMapping(UPDATE_PROJECT_PATH, consumes = ["application/json"])
    fun updateProject(@PathVariable pname: String,  @RequestBody newProj: ProjectsUpdateInputModel  ): SirenEntity<ProjectUpdatedResponse> {
        return projectservices.updateProject(pname, newProj).toSirenObject()
    }

    /**
     * Endpoint to delete a project.
     * DELETE /projects/{pname}
     */
    @AuthRequired
    @DeleteMapping(DELETE_PROJECT_PATH)
    fun deleteProject(@PathVariable pname: String): SirenEntity<ProjectDeletedResponse> {
        return projectservices.deleteProject(pname).toSirenObject()
    }

    /**
     * Endpoint to create a project label.
     * Post /projects/{pname}/label/{labelName}
     */
    @AuthRequired
    @PostMapping(CREATE_PROJECT_LABEL_PATH)
    fun createProjectLabel(@PathVariable pname: String, @PathVariable labelName: String): SirenEntity<ProjectLabelCreationResponse> {
        return projectservices.postProjectLabel(pname, labelName).toSirenObject()
    }

    /**
     * Endpoint to delete a project label.
     * DELETE /projects/{pname}/label/{labelName}
     */
    @AuthRequired
    @DeleteMapping(DELETE_PROJECT_LABEL_PATH)
    fun deleteProjectLabel(@PathVariable pname: String, @PathVariable labelName: String): SirenEntity<ProjectLabelDeleteResponse> {
        return projectservices.deleteProjectLabel(pname, labelName).toSirenObject()
    }
}