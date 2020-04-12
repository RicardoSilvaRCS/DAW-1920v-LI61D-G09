package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesOutputModel
import java.sql.Connection
import java.sql.SQLException

class GetProjIssues {
    /**
     * Endpoint responsible for obtaining the information of all existing issues of the specified Project
     * Needs to return:
     * -Name;
     * -Description;
     * -State;
     */

    companion object{

        private const val GET_ALL_ISSUES_FROM_PROJECT_QUERY : String = "select id,issuename,issuedescr,currstate from Issue where projname = ?;"

        fun execute(projName: String, conn: Connection): List<IssuesOutputModel> {
            val issues: MutableList<IssuesOutputModel> = mutableListOf()
            try {
                val ps = conn.prepareStatement(GET_ALL_ISSUES_FROM_PROJECT_QUERY)
                ps.use {
                    ps.setString(1,projName)
                    val rs = ps.executeQuery()
                    rs.use {
                        while(rs.next()){
                            issues.add(IssuesOutputModel(
                                    rs.getString("issuename"),
                                    rs.getString("issuedescr"),
                                    rs.getString("currstate"),
                                    rs.getInt("id")
                            ))
                        }
                    }
                }

            } catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error obtaining existing issues of project $projName, during access to internal database." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return issues
        }
    }
}