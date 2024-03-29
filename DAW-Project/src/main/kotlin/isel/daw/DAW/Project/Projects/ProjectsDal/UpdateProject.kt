package isel.daw.DAW.Project.Projects.ProjectsDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectUpdatedResponse
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsUpdateInputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

/**
 *  TODO: We need to figure out what to return in this function.
 */

class UpdateProject {

    /**
     * Endpoint responsible for updating a Project information
     * Can update the description for now.
     * Needs to receive:
     * -Name;
     * -Description;
     */

    companion object {

        private val UPDATE_PROJECT_INFO_QUERY = "update project set projdescr = ? where projname = ? ;"

        fun execute (projname: String, newProj: ProjectsUpdateInputModel, conn: Connection): ProjectUpdatedResponse {

            val ps : PreparedStatement
            try{
                conn.autoCommit = false
                ps = conn.prepareStatement(UPDATE_PROJECT_INFO_QUERY)
                ps.use {
                    ps.setString(1,newProj.newDescr)
                    ps.setString(2,projname)
                    ps.execute()
                }
                conn.commit()
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error during project update procedure." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return ProjectUpdatedResponse(projname)
        }
    }
}