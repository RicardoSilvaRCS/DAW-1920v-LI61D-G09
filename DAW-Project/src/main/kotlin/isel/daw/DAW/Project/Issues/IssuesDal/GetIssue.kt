package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInfoOutputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

/**
 *  TODO: The query to get all info, with all the joins and stuff
 *
 *  TODO: Decide what to do when an exception/error occurs.
 */
class GetIssue {
    /**
     * Endpoint responsible for obtaining the detailed information of an Issue
     * Needs to return:
     * -Id;
     * -Name;
     * -Description;
     * -Creation Date;
     * -Updated Date;
     * -Close Date;
     * -Current State;
     * -Assigned Labels
     * -Project Name
     */

    companion object{
        /**
         * Since there is a lot of information about an Issue that needs to be obtained
         * there will be the need of accessing multiple DB tables to get all the information.
         */
        private const val GET_ISSUE_INFO_QUERY : String =
                "SELECT issue.*, issuelabel.labelname\n" +
                "FROM issue\n" +
                   "LEFT JOIN issuelabel on issue.id = issuelabel.issueid\n" +
                "WHERE issue.id = ?"

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
                            issue.state = rs.getString("currstate")
                            do {
                                issue.labels.add(rs.getString("labelname")?:"")
                            } while(rs.next())
                        }
                    }
                }
            }catch ( ex : SQLException){

            } finally {
                conn.close()
            }
            return issue
        }
    }
}