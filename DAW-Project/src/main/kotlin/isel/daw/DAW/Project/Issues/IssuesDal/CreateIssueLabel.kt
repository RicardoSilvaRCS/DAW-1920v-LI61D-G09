package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Issues.IssuesDto.*
import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.SQLException
import java.time.LocalDate

class CreateIssueLabel {
    /**
     * Endpoint responsible for create a Issue label
     * Needs to receive:
     * -Id;
     * -labelName;
     * -projectName;
     */
    companion object {

        private const val INSERT_ISSUE_LABEL_QUERY = "insert into issuelabel(issueid, labelname, projname) " +
                "values (?, ?, ?)"

        fun execute(tid: Int, newIssueLabel: IssuesLabelInputModel, conn: Connection): IssueLabelPostResponse {
            val ps: PreparedStatement
            var tid: Int = -1

            try {
                conn.autoCommit = false
                ps = conn.prepareStatement(INSERT_ISSUE_LABEL_QUERY)

                ps.use {
                    ps.setInt(1, tid)
                    ps.setString(2, newIssueLabel.labelName)
                    ps.setString(3, newIssueLabel.projname)

                    val rs = ps.executeQuery()
                }
                conn.commit()
            } catch (ex: SQLException) {
                conn.rollback()
                throw InternalProcedureException("Error during issue label creation procedure." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return IssueLabelPostResponse()
        }
    }
}