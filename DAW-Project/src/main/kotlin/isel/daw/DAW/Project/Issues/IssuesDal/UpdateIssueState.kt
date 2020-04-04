package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Issues.IssuesDto.IssuesStateInputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsUpdateInputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class UpdateIssueState {
    /**
     * Endpoint responsible for updating a Issue state
     * Can update the current State
     * Needs to receive:
     * - issue id;
     * - new state
     */

    companion object {

        private const val UPDATE_ISSUE_STATE_QUERY = "update issue set currstate = ? where id = ? ;"

        fun execute (tid: Int, newState: IssuesStateInputModel, conn: Connection) {
            var ps : PreparedStatement

            try{
                ps = conn.prepareStatement(UPDATE_ISSUE_STATE_QUERY)
                ps.use {
                    ps.setString(1, newState.state)
                    ps.setInt(2, tid)
                    ps.execute()
                }

            }catch ( ex : SQLException){

            } finally {
                conn.close()
            }
        }
    }
}