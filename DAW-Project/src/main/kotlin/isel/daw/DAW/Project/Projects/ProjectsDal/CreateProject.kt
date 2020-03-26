package isel.daw.DAW.Project.Projects.ProjectsDal

import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInfoOutputModel
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInputModel
import java.sql.Connection
import java.sql.PreparedStatement


/**
 * TODO: We need to decide how are we gonna insert all the values.
 *      If we are going to do a stored procedure that inserts in all the necessary tables.(PBBLY THIS ONE)
 *      Or if we are gonna connect multiple times to the db inserting in a diferent table each time.
 *
 * TODO: We need to figure out what to return in this function.
 *
 * TODO: We have to implement the algorithm to check if the transitions given are valid
 *
 * TODO: We need to decide what to return when an exception/error occurs
 */


class CreateProject {

    /**
     * Endpoint responsible for creating a new Project
     * Needs to receive:
     * -Name;
     * -Description;
     * -Initial State;
     * -List of defined labels
     * -List of possible state transitions
     */

    companion object {
        /**
         * The creation of a new Project implies the insertion of tuples in various tables.
         */

        /**
         * A Project must always have the closed->archived transition
         */
        private val closedArchTrans: Pair<String, String> = Pair("closed", "archived")

        fun execute( newProject: ProjectsInputModel , conn : Connection) {

            throw NotImplementedError("TODO!")
        }
    }
}