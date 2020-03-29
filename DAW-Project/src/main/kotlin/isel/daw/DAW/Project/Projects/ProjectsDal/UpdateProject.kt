package isel.daw.DAW.Project.Projects.ProjectsDal

import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsUpdateInputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

/**
 *
 *  TODO: We need to figure out what to return in this function.
 *
 *  TODO: We need to decide what to return when an exception/error occurs
 */

class UpdateProject {

    /**
     * Endpoint responsible for updating a Project information
     * Can update the description
     * Needs to receive:
     * -Name;
     * -Description;
     */

    companion object {

        private val UPDATE_PROJECT_INFO_QUERY = "update project set projdescr = ? where projname = ? ;"

        fun execute ( projname: String, newProj: ProjectsUpdateInputModel, conn: Connection) {
            var ps : PreparedStatement

            try{
                ps = conn.prepareStatement(UPDATE_PROJECT_INFO_QUERY)
                ps.use {
                    ps.setString(1,newProj.newDescr)
                    ps.setString(2,projname)
                    ps.execute()
                }

            }catch ( ex : SQLException){

            } finally {
                conn.close()
            }
        }
    }
}