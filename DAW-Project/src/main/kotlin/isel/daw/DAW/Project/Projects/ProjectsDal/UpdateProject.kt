package isel.daw.DAW.Project.Projects.ProjectsDal

import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsUpdateInputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

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