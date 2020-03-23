package isel.daw.DAW.Project.Projects.ProjectsDal

import java.sql.Connection

/**
 *  TODO: Figure out what are we going to allow the user to update on a Project.
 *        For now it's only "description" but we have to decide if we will allow
 *        labels, states and transitions to be changed
 *        In the case we allow this 3 attributes to be updated we will need to:
 *          -Make sure the labels/states were also updated in the respective issues;
 *          -Implement the algorithm to check if the transitions given are valid.
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

        fun execute ( name: String, newDescr: String, conn: Connection) {
            throw NotImplementedError("TODO!")
        }
    }
}