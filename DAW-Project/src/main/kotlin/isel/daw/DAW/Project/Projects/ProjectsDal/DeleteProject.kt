package isel.daw.DAW.Project.Projects.ProjectsDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectDeletedResponse
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class DeleteProject {

    companion object {
        private const val DELETE_PROJECT_QUERY = "delete from project where projname = ? ;"

        fun execute(name: String, conn: Connection): ProjectDeletedResponse {
            val ps : PreparedStatement
            try{
                ps = conn.prepareStatement(DELETE_PROJECT_QUERY)
                ps.use {
                    ps.setString(1,name)
                    ps.execute()
                }
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error during project $name deletion." +
                        "Detailed pproblem: ${ex.message}")
            } finally {
                conn.close()
            }
            return ProjectDeletedResponse()
        }
    }
}