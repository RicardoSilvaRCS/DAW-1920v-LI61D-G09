package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Issues.IssuesDto.IssueUpdatedResponse
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInputModel
import isel.daw.DAW.Project.Projects.ProjectsDal.UpdateProject
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Timestamp

/**
 *  TODO we need to make Transaction scopes
 *
 *  Endpoint responsible for updating a issue information
 *  Can update the name and description
 *  Needs to receive:
 *  -Id;
 *  -Name;
 *  -Description;
 */
class UpdateIssue {

    companion object {

        private const val  UPDATE_ISSUE_QUERY = "update issue set issuename = ? , issuedescr = ? , updatedate = ?  where id = ? "

        fun execute(tid: Int, newIssue: IssuesInputModel, conn: Connection): IssueUpdatedResponse {
            val ps : PreparedStatement
            try{
                conn.autoCommit = false
                ps = conn.prepareStatement(UPDATE_ISSUE_QUERY)
                ps.use {
                    ps.setString(1, newIssue.name)
                    ps.setString(2, newIssue.descr)
                    ps.setTimestamp(3, Timestamp(System.currentTimeMillis()))
                    ps.setInt(4, tid)
                    ps.execute()
                }
                conn.commit()
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error during issue update procedure." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return IssueUpdatedResponse(tid, newIssue.projname)
        }
    }
}