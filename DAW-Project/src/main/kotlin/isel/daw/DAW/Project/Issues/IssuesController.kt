package isel.daw.DAW.Project.Issues

import isel.daw.DAW.Project.Common.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(ISSUES_PATH)
class IssuesController {

    /**
     * Endpoint to obtain all existing issues of a project. Must receive the @param project name. Might receive filtering parameters(creation-date, comment-number, etc.).
     * GET /issues/getissues projname={projName}
     */
    @GetMapping(GET_ISSUES_PATH)
    fun getissues( projName: String ) {
        throw NotImplementedError()
    }

    /**
     * Endpoint to obtain information of a specific issue. Must receive the @param id:Int.
     * GET /issues/getissue tid={issueId}
     */
    @GetMapping(GET_SINGLE_ISSUE_PATH)
    fun getissue( tid: Int ) {
        throw NotImplementedError()
    }

    /**
     * Endpoint to create a new Issue in a project. Must receive the following @params:
     *  projname:String, the name of the project;
     *  issname:String, the name of the issue;
     *  description:String, short description of the issue;
     *  labels:List<String>, the labels the issue has(might have none).
     * Might receive filtering parameters(creation-date, comment-number, etc.). The issues state must start with the initialstate defined in the project.
     * POST /issues/createissue projname={projName}&issname={issuename}&description={issuedescr}&labels={onelabel}
     */
    @PostMapping(CREATE_ISSUE_PATH)
    fun createissue() {
        throw NotImplementedError()
    }

    /**
     * Endpoint to update the issues information. Must receive the following (at least one of them)@params:
     *  issname:String, the new name of the issue;
     *  description:String, new description of the issue;
     *  labels:List<String>, a new label for the issue.
     * PUT /issues/updateissue/{tid} projname={projName}&issname={issuename}&description={issuedescr}&labels={onelabel}
     */
    @PutMapping(UPDATE_ISSUE_PATH)
    fun updateissueInfo(@PathVariable tid: Int) {
        throw NotImplementedError()
    }

    /**
     * Endpoint to update the issue state. Must receive the @param state:String.
     * PUT /issues/updateissue/{tid}/updatestate state={newstate}
     */
    @PutMapping(UPDATE_ISSUE_STATE_PATH)
    fun updateissuetate( @PathVariable tid: Int, state: String) {
        throw NotImplementedError()
    }

    /**
     * Endpoint to delete an issue.
     * DELETE /issues/deleteissue/{tid}
     */
    @DeleteMapping(DELETE_ISSUE_PATH)
    fun deleteissue(@PathVariable tid: Int) {
        throw NotImplementedError()
    }
}