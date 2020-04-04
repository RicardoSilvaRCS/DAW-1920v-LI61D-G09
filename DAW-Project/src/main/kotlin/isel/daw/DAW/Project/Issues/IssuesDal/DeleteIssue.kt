package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Projects.ProjectsDal.DeleteProject
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

        fun execute(tid: Int, conn: Connection) {
            var ps : PreparedStatement

            try{
                ps = conn.prepareStatement(DeleteIssue.DELETE_ISSUE_QUERY)
                ps.use {
                    ps.setInt(1,tid)
                    ps.execute()
                }

            }catch ( ex : SQLException){
                print(ex)
            } finally {
                conn.close()
            }
        }
    }
}