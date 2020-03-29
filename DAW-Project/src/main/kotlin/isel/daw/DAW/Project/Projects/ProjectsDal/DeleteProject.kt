package isel.daw.DAW.Project.Projects.ProjectsDal

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

/**
 *
 * TODO: We need to figure out what to return in this function.
 *
 * TODO: We need to decide what to return when an exception/error occurs
 */


class DeleteProject {

    /**
     * Endpoint responsible for deleting a Project
     * Needs to receive:
     * -Name;
     */

    companion object {
        private val DELETE_PROJECT_QUERY = "delete from project where projname = ? ;"

        fun execute(name: String, conn: Connection) {
            var ps : PreparedStatement

            try{
                ps = conn.prepareStatement(DeleteProject.DELETE_PROJECT_QUERY)
                ps.use {
                    ps.setString(1,name)
                    ps.execute()
                }

            }catch ( ex : SQLException){
                print(ex)
            } finally {
                conn.close()
            }
        }
    }
}