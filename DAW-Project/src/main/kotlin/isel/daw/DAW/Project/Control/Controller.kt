package isel.daw.DAW.Project.Control

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

class Controller () {

    //Projects

    /*
    Endpoint to obtain all existing projects. Might receive filtering parameters(creation-date, issue-number, etc.).
    GET /projects/getprojects
     */
    @GetMapping("/projects/getprojects")
    fun getProjects () {
        throw NotImplementedError()
    }

    /*
    Endpoint to obtain detailed information of a single project. Must receive the @param name:String that identifies the project.
    GET /projects/getproject name={ProjName}
     */
    @GetMapping("/projects/getproject")
    fun getProject( name: String ) {
        throw NotImplementedError()
    }

    /*
    Endpoint to create a new project. Must receive the following @params:
        name:String, the name of the project;
        description:String, a short description;
        labels:List<String>, the labels used in the projects context;
        initstate:String, initial state used in the projects context;
        transitions:List<String>, possible state transitions.
    POST /projects/createproject name={ProjName}&description={projDescr}&labels={onelabel}&labels={twolabel}&initstate={initialState}&transitions={onestate~otherstate}
     */
    @PostMapping("/projects/createproject")
    fun createProject( name: String , description: String , labels: List<String> , initstate: String , transitions: List<String> ) {
        throw NotImplementedError()
    }

    /*
    Endpoint to update a projects information. For now only receives a new @param description:String.
    PUT /projects/updateproject description={newProjDescr}
     */
    @PutMapping("/projects/updateproject")
    fun updateProject( description: String ) {
        throw NotImplementedError()
    }

    /*
    Endpoint to delete a project. Must receive the @param name:String of the project.
    DELETE /projects/deleteproject name={projName}
     */
    @DeleteMapping("/projects/deleteproject")
    fun deleteProject( name: String ) {
        throw NotImplementedError()
    }

    //----------------------------//---------------------------------

    //Issues

    /*
    Endpoint to obtain all existing issues of a project. Must receive the @param project name. Might receive filtering parameters(creation-date, comment-number, etc.).
    GET /issues/getissues projname={projName}
     */
    @GetMapping("/issues/getissues")
    fun getissues( projName: String ) {
        throw NotImplementedError()
    }

    /*
    Endpoint to obtain information of a specific issue. Must receive the @param id:Int.
    GET /issues/getissue tid={issueId}
     */
    @GetMapping("/issues/getissue")
    fun getissue( tid: Int ) {
        throw NotImplementedError()
    }

    /*
    Endpoint to create a new Issue in a project. Must receive the following @params:
        projname:String, the name of the project;
        issname:String, the name of the issue;
        description:String, short description of the issue;
        labels:List<String>, the labels the issue has(might have none).
    Might receive filtering parameters(creation-date, comment-number, etc.). The issues state must start with the initialstate defined in the project.
    POST /issues/createissue projname={projName}&issname={issuename}&description={issuedescr}&labels={onelabel}
     */
    @PostMapping("/issues/createissue")
    fun createissue() {
        throw NotImplementedError()
    }

    /*
    Endpoint to update the issues information. Must receive the following (at least one of them)@params:
        issname:String, the new name of the issue;
        description:String, new description of the issue;
        labels:List<String>, a new label for the issue.
    PUT /issues/updateissue/:tid projname={projName}&issname={issuename}&description={issuedescr}&labels={onelabel}
     */
    @PutMapping("/issues/updateissue/:tid")
    fun updateissueInfo() {
        throw NotImplementedError()
    }

    /*
    Endpoint to update the issue state. Must receive the @param state:String.
    PUT /issues/updateissue/:tid/updatestate state={newstate}
     */
    @PutMapping("/issues/updateissue/:tid/updatestate")
    fun updateissuetate( state: String) {
        throw NotImplementedError()
    }

    /*
    Endpoint to delete an issue.
    DELETE /issues/deleteissue/:tid
     */
    @DeleteMapping("/issues/deleteissue/:tid")
    fun deleteissue() {
        throw NotImplementedError()
    }

    //----------------------------//---------------------------------

    //Comments
    /*
    Endpoint to get all comments of an issue.
    GET /comments/getcomments/:tid
     */
    @GetMapping("/comments/getcomments/:tid")
    fun getComments() {
        throw NotImplementedError()
    }

    /*
    Endpoint to add a comment to an issue. Must receive the @param text:String.
    POST /comments/createcomment/:tid text={commenttext}
     */
    @PostMapping("/comments/createcomment/:tid")
    fun createComment( text: String) {
        throw NotImplementedError()
    }

    /*
    Endpoint to delete a comment of an issue.
    DELETE /comments/deletecomment/:cid
     */
    @DeleteMapping("/comments/deletecomment/:cid")
    fun deleteComment () {
        throw NotImplementedError()
    }

}