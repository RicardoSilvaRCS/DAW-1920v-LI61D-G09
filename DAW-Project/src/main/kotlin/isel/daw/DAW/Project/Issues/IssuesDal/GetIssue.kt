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
        private const val GET_ISSUE_INFO_QUERY : String = ""

        fun execute(projectName : String , conn : Connection): IssuesInfoOutputModel {

            var issue = IssuesInfoOutputModel()
            var ps : PreparedStatement

            try{
                ps = conn.prepareStatement(GET_ISSUE_INFO_QUERY)
                ps.use {

                }
            }catch ( ex : SQLException){

            } finally {
                conn.close()
            }
            return issue
        }
    }
}