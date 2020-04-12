package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInputModel
import isel.daw.DAW.Project.Projects.ProjectsDal.CreateProject
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInputModel
import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.SQLException
import java.time.LocalDate

/**
 *  TODO: Needs to be implemented :)
 *
 *  TODO: Decide what to do when an exception/error occurs.
 *
 *  TODO: We need to figure out what to return in this function.
 *
 *  TODO we need to make Transaction scopes
 */
class CreateIssue {

    companion object {

        private const val INSERT_ISSUE_QUERY = "INSERT INTO public.issue" +
                " (issuename, issuedescr, creationdate, updatedate, projname, currstate)" +
                " VALUES (?, ?, ?, ?, ?, ?) returning issue.id"

        fun execute(newIssue: IssuesInputModel, conn: Connection) {
            val ps : PreparedStatement

            try{
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
                            insertLabels(newIssue, rs.getInt("id"), conn)
                        }
                }
            }catch ( ex : SQLException){
                print(ex)
            } finally {
                conn.close()
            }
        }

        /**-----------------------------------------INSER LABELS-----------------------------------------------------*/
        private const val INSERT_ISSUE_LABELS_QUERY = "INSERT INTO public.issuelabel" +
                " (issueid, labelname, projname) " +
                " VALUES (?, ?, ?);"

        private fun insertLabels (newIssue: IssuesInputModel, id : Int, conn: Connection) {
            val ps = conn.prepareStatement(INSERT_ISSUE_LABELS_QUERY)

            ps.use {
                newIssue.labels.forEach {
                    ps.setInt(1,id)
                    ps.setString(2,it)
                    ps.setString(3,newIssue.projname)
                }
                ps.execute()
            }

        }

    }
}