package isel.daw.DAW.Project.Issues

import isel.daw.DAW.Project.Issues.IssuesDal.DeleteIssue
import isel.daw.DAW.Project.Issues.IssuesDal.GetIssue
import isel.daw.DAW.Project.Issues.IssuesDal.GetProjIssues
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInfoOutputModel
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInputModel
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesOutputModel
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

    fun create(newIssue: IssuesInputModel) {
        throw NotImplementedError("TODO!")
    }

    fun updateInfo(tid: Int, newIssue: IssuesInputModel) {
        throw NotImplementedError("TODO!")
    }

    fun updatestate( tid: Int, newState: String) {
        throw NotImplementedError("TODO!")
    }

    fun delete( tid: Int) {
        return DeleteIssue.execute(tid,dbs.connection)
    }
}