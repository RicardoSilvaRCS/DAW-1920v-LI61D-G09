package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Issues.IssuesDto.IssueStateUpdatedResponse
import isel.daw.DAW.Project.Issues.IssuesDto.IssueUpdatedResponse
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesStateInputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.FINAL_STATE
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsUpdateInputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Timestamp

class UpdateIssueState {
    /**
     * Endpoint responsible for updating a Issue state
     * Can update the current State
     * Needs to receive:
     * - issue id;
     * - new state
     */

    companion object {

        private const val UPDATE_ISSUE_STATE_QUERY = "update issue set currstate = ? , updatedate = ? where id = ? ;"

        private const val UPDATE_ISSUE_STATE_ARCHIVED_QUERY = "update issue set currstate = ? , closedate = ? where id = ? ;"

        fun execute (tid: Int, newState: IssuesStateInputModel, conn: Connection): IssueStateUpdatedResponse {
            var ps : PreparedStatement

            try{
                conn.autoCommit = false
                if(newState.state.equals(FINAL_STATE)) {
                    ps = conn.prepareStatement(UPDATE_ISSUE_STATE_ARCHIVED_QUERY)
                } else {
                    ps = conn.prepareStatement(UPDATE_ISSUE_STATE_QUERY)
                }
                ps.use {
                    ps.setString(1, newState.state)
                    ps.setTimestamp(2, Timestamp(System.currentTimeMillis()))
                    ps.setInt(3, tid)
                    ps.execute()
                }
                conn.commit()
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error during issue state update procedure." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return IssueStateUpdatedResponse(tid)
        }
    }
}