package isel.daw.DAW.Project.Issues

import isel.daw.DAW.Project.Issues.IssuesDal.*
import isel.daw.DAW.Project.Issues.IssuesDto.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.sql.DataSource

/**
 * Repository for acessing Issues related data
 */
@Repository
@Component
class IssuesRepository(@Autowired val dbs: DataSource)  {

    fun getIssuesOfProj( projName: String): List<IssuesOutputModel> {
        return GetProjIssues.execute(projName, dbs.connection)
    }

    fun getById( tid: Int ): IssuesInfoOutputModel {
        return GetIssue.execute(tid, dbs.connection)
    }

    fun create(newIssue: IssuesInputModel): IssueCreationResponse {
        return CreateIssue.execute(newIssue, dbs.connection)
    }

    fun updateInfo(tid: Int, newIssue: IssuesInputModel): IssueUpdatedResponse {
        return UpdateIssue.execute(tid, newIssue, dbs.connection)
    }

    fun updatestate( tid: Int, newState: IssuesStateInputModel): IssueStateUpdatedResponse {
        return UpdateIssueState.execute(tid, newState, dbs.connection)
    }

    fun delete( tid: Int): IssueDeletedResponse {
        return DeleteIssue.execute(tid,dbs.connection)
    }
}