package isel.daw.DAW.Project.Issues

import isel.daw.DAW.Project.Common.*
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInfoOutputModel
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInputModel
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesOutputModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(ISSUES_PATH)
class IssuesController(val issuesrepo: IssuesRepository) {

    /**
     * Endpoint to obtain all existing issues of a project. Might receive filtering parameters(creation-date, comment-number, etc.).
     * GET /issues/getissues/{pname}
     */
    @GetMapping(GET_ISSUES_PATH)
    fun getissues( @PathVariable pname: String): List<IssuesOutputModel> {
        return issuesrepo.getIssuesOfProj(pname)
    }

    /**
     * Endpoint to obtain information of a specific issue. Must receive the @param id:Int.
     * GET /issues/getissue?tid={issueId}
     */
    @GetMapping(GET_SINGLE_ISSUE_PATH)
    fun getissue( tid: Int ): IssuesInfoOutputModel {
        return issuesrepo.getById(tid)
    }

    /**
     * Endpoint to create a new Issue in a project. Must receive the following @params:
     *  projname:String, the name of the project;
     *  issname:String, the name of the issue;
     *  description:String, short description of the issue;
     *  labels:Array<String>, the labels the issue has(might have none).
     * Might receive filtering parameters(creation-date, comment-number, etc.). The issues state must start with the initialstate defined in the project.
     * POST /issues/createissue
     */
    @PostMapping(CREATE_ISSUE_PATH, consumes = ["application/json"])
    fun createissue(@RequestBody newIssue: IssuesInputModel) {
        throw NotImplementedError("TODO!")
    }

    /**
     * Endpoint to update the issues information. Must receive the following (at least one of them)@params:
     *  issname:String, the new name of the issue;
     *  description:String, new description of the issue;
     *  labels:Array<String>, a new label for the issue.
     * PUT /issues/updateissue/{tid}
     */
    @PutMapping(UPDATE_ISSUE_PATH)
    fun updateissueInfo(@PathVariable tid: Int, @RequestBody newIssue: IssuesInputModel) {
        throw NotImplementedError("TODO!")
    }

    /**
     * Endpoint to update the issue state. Must receive the @param state:String.
     * PUT /issues/updateissue/{tid}/updatestate
     */
    @PutMapping(UPDATE_ISSUE_STATE_PATH, consumes = ["application/json"])
    fun updateissuetate( @PathVariable tid: Int, @RequestBody state: String) {
        throw NotImplementedError("TODO!")
    }

    /**
     * Endpoint to delete an issue.
     * DELETE /issues/deleteissue/{tid}
     */
    @DeleteMapping(DELETE_ISSUE_PATH)
    fun deleteissue(@PathVariable tid: Int) {
        throw NotImplementedError("TODO!")
    }
}