package isel.daw.DAW.Project.Projects.ProjectsDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectDeletedResponse
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectLabelDeleteResponse
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class DeleteProjectLabel {
    /**
     * Endpoint responsible for delete a Project label
     * Needs to receive:
     * -labelName;
     * -projectName;
     */
    companion object {
        private const val DELETE_PROJECT_LABEL_QUERY = "delete from projectlabel where projname = ? and labelName = ?;"

        fun execute(projectName: String, labelName: String, conn: Connection): ProjectLabelDeleteResponse {
            val ps : PreparedStatement
            try{
                ps = conn.prepareStatement(DELETE_PROJECT_LABEL_QUERY)
                ps.use {
                    ps.setString(1, projectName)
                    ps.setString(2, labelName)

                    ps.execute()
                }
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error during project label deletion." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return ProjectLabelDeleteResponse()
        }
    }
}