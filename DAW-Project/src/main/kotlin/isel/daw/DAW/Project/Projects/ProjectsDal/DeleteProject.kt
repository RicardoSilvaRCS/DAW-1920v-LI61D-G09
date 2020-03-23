package isel.daw.DAW.Project.Projects.ProjectsDal

import java.sql.Connection

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
        fun execute(name: String, conn: Connection) {
            throw NotImplementedError("TODO!")
        }
    }
}