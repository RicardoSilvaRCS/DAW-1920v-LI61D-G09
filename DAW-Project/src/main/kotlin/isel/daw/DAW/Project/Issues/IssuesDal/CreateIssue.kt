package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Issues.IssuesDto.IssueCreationResponse
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInputModel
import isel.daw.DAW.Project.Projects.ProjectsDal.CreateProject
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInputModel
import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.SQLException
import java.time.LocalDate

/**
 *  TODO we need to make Transaction scopes
 */

class CreateIssue {

    companion object {

        private const val INSERT_ISSUE_QUERY = "INSERT INTO public.issue" +
                " (issuename, issuedescr, creationdate, updatedate, projname, currstate)" +
                " VALUES (?, ?, ?, ?, ?, ?) returning issue.id"

        fun execute(newIssue: IssuesInputModel, conn: Connection): IssueCreationResponse {
            val ps : PreparedStatement
            var tid: Int = -1

            try{
                conn.autoCommit = false
                ps = conn.prepareStatement(INSERT_ISSUE_QUERY)

                ps.use {
                        ps.setString(1,newIssue.name)
                        ps.setString(2,newIssue.descr)
                        ps.setDate(3, Date.valueOf(LocalDate.now()))
                        ps.setDate(4,Date.valueOf(LocalDate.now()))
                        ps.setString(5,newIssue.projname)
                        ps.setString(6,newIssue.currState)
                        val rs = ps.executeQuery()
                        if(rs.next()) {
                            tid = rs.getInt("id")
                            insertLabels(newIssue, tid, conn)
                        }

                }
                conn.commit()
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error during issue creation procedure." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return IssueCreationResponse(tid, newIssue.projname)
        }

        /**-----------------------------------------INSER LABELS-----------------------------------------------------*/
        private const val INSERT_ISSUE_LABELS_QUERY = "INSERT INTO public.issuelabel" +
                " (issueid, labelname, projname) " +
                " VALUES (?, ?, ?);"

        private fun insertLabels (newIssue: IssuesInputModel, id : Int, conn: Connection) {
            try {
                val ps = conn.prepareStatement(INSERT_ISSUE_LABELS_QUERY)

                ps.use {
                    newIssue.labels.forEach {
                        ps.setInt(1,id)
                        ps.setString(2,it)
                        ps.setString(3,newIssue.projname)
                    }
                    ps.execute()
                }
            } catch (ex: SQLException) {
                conn.rollback()
                throw InternalProcedureException("Error during issue labels insertion procedure." +
                        "Detailed problem: ${ex.message}")
            }
        }

    }
}