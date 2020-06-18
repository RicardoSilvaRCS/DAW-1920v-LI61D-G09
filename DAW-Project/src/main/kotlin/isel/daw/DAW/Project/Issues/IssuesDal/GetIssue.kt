package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInfoOutputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class GetIssue {

    companion object{

        private const val GET_ISSUE_INFO_QUERY : String =
                " SELECT issue.*, issuelabel.labelname , statetransitions.currstate as currstatetran  , statetransitions.nextstate" +
                        " FROM issue" +
                        " LEFT JOIN issuelabel on issue.id = issuelabel.issueid" +
                        " INNER JOIN statetransitions on statetransitions.projname = issue.projname" +
                        " where issue.currstate in (statetransitions.currstate , statetransitions.nextstate)" +
                        " and issue.id = ?"

        fun execute(issueId : Int , conn : Connection): IssuesInfoOutputModel {

            val issue = IssuesInfoOutputModel()
            val ps : PreparedStatement

            try{
                ps = conn.prepareStatement(GET_ISSUE_INFO_QUERY)
                ps.use {
                    ps.setInt(1, issueId)
                    val rs = ps.executeQuery()
                    rs.use {
                        if(rs.next()) {
                            issue.id = rs.getInt("id")
                            issue.name = rs.getString("issuename")
                            issue.descr = rs.getString("issuedescr")
                            issue.creationDate = rs.getTimestamp("creationdate")
                            issue.updateDate = rs.getTimestamp("updatedate")
                            issue.closeDate = rs.getTimestamp("closedate")
                            issue.projname = rs.getString("projname")
                            issue.currState = rs.getString("currstate")
                            do {

                                issue.labels.add(rs.getString("labelname")?:"")
                                val auxCurrState = rs.getString("currstatetran")
                                val auxNextState = rs.getString("nextstate")

                                if(issue.currState != "Archived") {
                                    if (issue.currState.equals(auxCurrState)) {
                                        issue.possibleNextStates.add(auxNextState)
                                    } else {
                                        issue.possibleNextStates.add(auxCurrState)
                                    }
                                }
                            } while(rs.next())
                        }
                    }
                }
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error obtaining issue with id '$issueId', during access to internal database." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return issue
        }
    }
}