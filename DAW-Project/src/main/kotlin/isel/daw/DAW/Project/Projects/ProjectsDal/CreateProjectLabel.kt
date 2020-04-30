package isel.daw.DAW.Project.Projects.ProjectsDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectLabelCreationResponse
import java.sql.Connection
import java.sql.SQLException

class CreateProjectLabel {
    /**
     * Endpoint responsible for create a Project label
     * Needs to receive:
     * -labelName;
     * -projectName;
     */
    companion object {

        private const val INSERT_PROJECT_LABEL: String = "insert into projectlabel(labelname, projname) " +
                "values(?,?);"

        fun execute(projectName: String, labelName: String, conn: Connection): ProjectLabelCreationResponse {

            try {
                conn.autoCommit = false
                val ps = conn.prepareStatement(INSERT_PROJECT_LABEL)

                ps.use {
                    ps.setString(1, labelName)
                    ps.setString(2, projectName)
                    ps.execute()
                }

                conn.commit()
            } catch (ex: SQLException) {
                conn.rollback()
                throw InternalProcedureException("Error during project label creation procedure." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return ProjectLabelCreationResponse(projectName)
        }
    }
}