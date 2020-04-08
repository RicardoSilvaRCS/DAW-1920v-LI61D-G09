package isel.daw.DAW.Project.Issues

import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInfoOutputModel
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInputModel
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesOutputModel
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesStateInputModel
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Services layer for Issues related operations. All the business logic is implemented in this layer.
 *
 * TODO: We need to make our validations.
 */
@Component
@Service
class IssuesServices(private val issuesrepo: IssuesRepository) {

    fun getIssues( projName: String): List<IssuesOutputModel> {
        return issuesrepo.getIssuesOfProj(projName)
    }

    fun getIssue( tid: Int ): IssuesInfoOutputModel {
        return issuesrepo.getById(tid)
    }

    fun createIssue(newIssue: IssuesInputModel) {
        return issuesrepo.create(newIssue)
    }

    fun updateIssue(tid: Int, newIssue: IssuesInputModel) {
        return issuesrepo.updateInfo(tid, newIssue)
    }

    fun updateState( tid: Int, newState: IssuesStateInputModel) {
        return issuesrepo.updatestate(tid, newState)
    }

    fun deleteIssue( tid: Int) {
        return issuesrepo.delete(tid)
    }

}