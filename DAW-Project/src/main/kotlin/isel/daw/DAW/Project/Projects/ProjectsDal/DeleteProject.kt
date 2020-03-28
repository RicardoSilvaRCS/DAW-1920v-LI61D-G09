package isel.daw.DAW.Project.Projects.ProjectsDal

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

/**
 * TODO: We need to decide how are we gonna delete all the values.
 *      If we are going to do a stored procedure that deletes in all the necessary tables in the correct order.(PBBLY THIS ONE)
 *      Or if we are gonna connect multiple times to the db deleting in a diferent table each time.
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
        /**
         * The removal of a Project implies the removal of several tuples in various tables.
         */
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