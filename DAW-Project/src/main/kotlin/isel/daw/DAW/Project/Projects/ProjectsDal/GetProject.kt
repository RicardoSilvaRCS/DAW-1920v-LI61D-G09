package isel.daw.DAW.Project.Projects.ProjectsDal


import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInfoOutputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class GetProject {

    /**
     * Endpoint responsible for obtaining the detailed information of a Project
     * Needs to return:
     * -Name;
     * -Description;
     * -Initial State;
     * -List of defined labels
     * -List of available states
     * -List of possible state transitions
     */

    companion object {
        /**
         * Since there is a lot of information about a Project that needs to be obtained
         * there will be the need of accessing multiple DB tables to get all the information.
         */

        private const val GET_PROJECT_INFO_QUERY: String = "SELECT proj.projname, projdescr, projinitstate , prjlabel.labelname , projectstate.statename , statetrans.currstate,statetrans.nextstate\n" +
                "\tFROM project proj inner join projectlabel prjlabel \n" +
                "\ton proj.projname = prjlabel.projname \n" +
                "\tinner join projectstate on proj.projname = projectstate.projname\n" +
                "\tinner join statetransitions statetrans on proj.projname = statetrans.projname\n" +
                "\twhere proj.projname = ?"

        fun execute(projectName: String, conn: Connection): ProjectsInfoOutputModel {

            val project = ProjectsInfoOutputModel()
            val ps: PreparedStatement
            try {
                ps = conn.prepareStatement(GET_PROJECT_INFO_QUERY)
                ps.use {
                    ps.setString(1, projectName)
                    val rs = ps.executeQuery()
                    rs.use {

                        if (rs.next()) {
                            project.name = rs.getString("projname")
                            project.descr = rs.getString("projdescr")
                            project.initstate = rs.getString("projinitstate")
                            do {
                                val label = rs.getString("labelname")
                                if (!project.labels.contains(label)) {
                                    project.labels.add(label)
                                }
                                val stateName = rs.getString("statename")
                                if (!project.states.contains(stateName)) {
                                    project.states.add(stateName)
                                }
                                val transitions = Pair(rs.getString("currstate"), rs.getString("nextstate"))
                                if (!project.transitions.contains(transitions)) {
                                    project.transitions.add(transitions)
                                }
                            } while (rs.next())
                        }
                    }
                }
            } catch (ex: SQLException) {
                throw InternalProcedureException("Error during access to internal database obtaining project $projectName." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return project
        }

    }
}