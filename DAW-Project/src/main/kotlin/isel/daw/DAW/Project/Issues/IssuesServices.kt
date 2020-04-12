package isel.daw.DAW.Project.Issues

import isel.daw.DAW.Project.Common.IllegalIssueStateException
import isel.daw.DAW.Project.Common.InvalidIssueException
import isel.daw.DAW.Project.Common.InvalidResourceRequestedException
import isel.daw.DAW.Project.Common.IssuesNotFoundException
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInfoOutputModel
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInputModel
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesOutputModel
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesStateInputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInfoOutputModel
import isel.daw.DAW.Project.Projects.ProjectsRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Services layer for Issues related operations. All the business logic is implemented in this layer.
 */

@Component
@Service
class IssuesServices(private val issuesrepo: IssuesRepository, private val projectsrepo: ProjectsRepository) {

    fun getIssues(projName: String): List<IssuesOutputModel> {
        if(projName.isEmpty() || projName.isBlank()) {
            throw InvalidResourceRequestedException("No project name received.")
        }
        val issues = issuesrepo.getIssuesOfProj(projName)
        if(issues.isEmpty()) {
            throw IssuesNotFoundException("No issues found for project $projName.")
        }
        return issues
    }

    fun getIssue( tid: Int ): IssuesInfoOutputModel {
        if(tid<0) {
            throw InvalidResourceRequestedException("Issue id received is invalid, can't be negative.")
        }
        return issuesrepo.getById(tid)
    }

    fun createIssue(newIssue: IssuesInputModel) {
        if(!newIssue.isValid()) {
            throw InvalidIssueException("Invalid information received for the creation of an issue.")
        }
        val projOfIssue: ProjectsInfoOutputModel = projectsrepo.getByName(newIssue.name)
        if(projOfIssue.isDefault()) {
            throw InvalidResourceRequestedException("The project in which the issue wants to be inserted doesn't exist.")
        }
        if(!newIssue.currState.equals(projOfIssue.initstate)) {
            throw IllegalIssueStateException("The issue starting state state received: '${newIssue.currState}' doesn't match the project's inital state: '${projOfIssue.initstate}'.")
        }
        return issuesrepo.create(newIssue)
    }

    fun updateIssue(tid: Int, newIssue: IssuesInputModel) {
        return issuesrepo.updateInfo(tid, newIssue)
    }

    fun updateState( tid: Int, newState: IssuesStateInputModel) {
        if(tid<0) {
            throw InvalidResourceRequestedException("Issue id received is invalid, can't be negative.")
        }
        if(!newState.isValid()) {
            throw IllegalIssueStateException("The issue state received is not valid, can't be empty or blank.")
        }
        return issuesrepo.updatestate(tid, newState)
    }

    fun deleteIssue( tid: Int) {
        if(tid<0) {
            throw InvalidResourceRequestedException("Issue id received is invalid, can't be negative.")
        }
        return issuesrepo.delete(tid)
    }

}