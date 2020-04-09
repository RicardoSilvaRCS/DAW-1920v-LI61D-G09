package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInfoOutputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

/**
 *  TODO: Decide what to do when an exception/error occurs.
 */

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

            var issue = IssuesInfoOutputModel()
            var ps : PreparedStatement

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
                                var auxCurrState = rs.getString("currstatetran")
                                var auxNextState = rs.getString("nextstate")

                                if(issue.currState.equals(auxCurrState)){
                                    issue.possibleNextStates.add(auxNextState)
                                }else{
                                    issue.possibleNextStates.add(auxCurrState)
                                }
                            } while(rs.next())
                        }
                    }
                }
            }catch ( ex : SQLException){
                var msg = ex.message
            } finally {
                conn.close()
            }
            return issue
        }
    }
}