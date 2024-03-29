package isel.daw.DAW.Project.Projects.ProjectsDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsOutputModel
import java.sql.Connection
import java.sql.SQLException

class GetProjects {
    /**
     * Endpoint responsible for obtaining the information of all existing Projects
     * Needs to return:
     * -Name;
     * -Description;
     */

    companion object{

        private const val GET_ALL_PROJECTS_QUERY : String = "select p.projName ,p.projDescr " +
                "from Project p inner join usersprojects up " +
                "on (p.projName = up.projname) " +
                "where username = ?"

        fun execute(userName : String , conn: Connection): List<ProjectsOutputModel> {
            val projects: MutableList<ProjectsOutputModel> = mutableListOf()
            try {
                val ps = conn.prepareStatement(GET_ALL_PROJECTS_QUERY)
                ps.use {
                    ps.setString(1,userName)
                    val rs = ps.executeQuery()
                    rs.use {
                        while(rs.next()){
                                projects.add(ProjectsOutputModel(
                                    rs.getString("projName"),
                                    rs.getString("projDescr")
                            ))
                        }
                    }
                }
            } catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error obtaining existing projects, during access to internal database." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return projects
        }
    }

}