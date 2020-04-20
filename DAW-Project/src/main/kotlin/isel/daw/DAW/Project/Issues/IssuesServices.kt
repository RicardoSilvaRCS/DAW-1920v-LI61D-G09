package isel.daw.DAW.Project.Issues

import isel.daw.DAW.Project.Common.IllegalIssueStateException
import isel.daw.DAW.Project.Common.InvalidIssueException
import isel.daw.DAW.Project.Common.InvalidResourceRequestedException
import isel.daw.DAW.Project.Common.IssuesNotFoundException
import isel.daw.DAW.Project.Issues.IssuesDto.*
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

    fun createIssue(newIssue: IssuesInputModel): IssueCreationResponse {
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

    fun updateIssue(tid: Int, newIssue: IssuesInputModel): IssueUpdatedResponse {
        return issuesrepo.updateInfo(tid, newIssue)
    }

    fun updateState( tid: Int, newState: IssuesStateInputModel): IssueStateUpdatedResponse {
        if(tid<0) {
            throw InvalidResourceRequestedException("Issue id received is invalid, can't be negative.")
        }
        if(!newState.isValid()) {
            throw IllegalIssueStateException("The issue state received is not valid, can't be empty or blank.")
        }
        return issuesrepo.updatestate(tid, newState)
    }

    fun deleteIssue( tid: Int): IssueDeletedResponse {
        if(tid<0) {
            throw InvalidResourceRequestedException("Issue id received is invalid, can't be negative.")
        }
        return issuesrepo.delete(tid)
    }

    fun createIssueLabel(tid: Int, newIssueLabel : IssuesLabelInputModel) : IssueLabelCreationResponse{
        return issuesrepo.createLabel(tid, newIssueLabel)
    }

    fun deleteIssueLabel(tid: Int, projName: String) : IssueLabelDeletedResponse{
        return issuesrepo.deleteLabel(tid, projName)
    }
}