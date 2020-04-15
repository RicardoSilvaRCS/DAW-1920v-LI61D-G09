package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Issues.IssuesDto.IssueDeletedResponse
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class DeleteIssue {

    /**
     * Endpoint responsible for deleting a Issue
     * Needs to receive:
     * -Id;
     */

    companion object {
        private const val DELETE_ISSUE_QUERY = "delete from Issue where Id = ? ;"

        fun execute(tid: Int, conn: Connection): IssueDeletedResponse {
            val ps : PreparedStatement

            try{
                ps = conn.prepareStatement(DeleteIssue.DELETE_ISSUE_QUERY)
                ps.use {
                    ps.setInt(1,tid)
                    ps.execute()
                }

            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error during issue deletion procedure." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }

            return IssueDeletedResponse()
        }
    }
}