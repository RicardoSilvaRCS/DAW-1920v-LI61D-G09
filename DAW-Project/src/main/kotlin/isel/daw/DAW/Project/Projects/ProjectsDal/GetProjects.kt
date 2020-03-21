package isel.daw.DAW.Project.Projects.ProjectsDal

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

        private const val GET_ALL_PROJECTS_QUERY : String = "select projName,projDescr from Project"

        fun execute(conn: Connection): List<ProjectsOutputModel> {
            var projects: List<ProjectsOutputModel> = emptyList()
            try {
                val ps = conn.prepareStatement(GET_ALL_PROJECTS_QUERY)
                ps.use {
                    val rs = ps.executeQuery()
                    rs.use {
                        while(rs.next()){
                            projects = projects.plus(ProjectsOutputModel(
                                    rs.getString("projName"),
                                    rs.getString("projDescr")
                            ))
                        }
                    }
                }

            } catch ( ex : SQLException){

            } finally {
                conn.close()
            }
            return projects
        }
    }

}