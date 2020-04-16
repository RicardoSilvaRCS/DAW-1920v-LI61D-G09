package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Issues.IssuesDto.IssueDeletedResponse
import isel.daw.DAW.Project.Issues.IssuesDto.IssueLabelDeletedResponse
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class DeleteIssueLabel {
    /**
     * Endpoint responsible for remove a Issue label
     * Needs to receive:
     * -Id;
     * -labelName;
     */

    companion object {
        private const val DELETE_ISSUE_LABEL_QUERY = "delete from issuelabel where issueid = ? and labelname = ?"

        fun execute(tid: Int, labelName :String, conn: Connection): IssueLabelDeletedResponse {
            val ps : PreparedStatement

            try{
                ps = conn.prepareStatement(DeleteIssueLabel.DELETE_ISSUE_LABEL_QUERY)
                ps.use {
                    ps.setInt(1,tid)
                    ps.setString(2, labelName)
                    ps.execute()
                }

            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error during issue label deletion procedure." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }

            return IssueLabelDeletedResponse()
        }
    }
}