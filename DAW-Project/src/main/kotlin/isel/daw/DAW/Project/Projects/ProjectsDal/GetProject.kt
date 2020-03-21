package isel.daw.DAW.Project.Projects.ProjectsDal

import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInfoOutputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
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

    companion object{
        /**
         * Since there is a lot of information about a Project that needs to be obtained
         * there will be the need of accessing multiple DB tables to get all the information.
         */
        private const val GET_PROJECT_INFO_QUERY : String = "select projName,projDescr,projInitState from Project where projname=?"
        private const val GET_PROJECT_LABELS_QUERY : String = "select labelName from ProjectLabel where projname=?"
        private const val GET_PROJECT_STATES_QUERY : String = "select stateName from ProjectState where projname=?"
        private const val GET_PROJECT_STATE_TRANSITIONS_QUERY : String = "select currState,nextState from StateTransitions where projname=?"

        fun execute(projectName : String , conn : Connection): ProjectsInfoOutputModel {

            var project : ProjectsInfoOutputModel = ProjectsInfoOutputModel(projectName)
            var ps : PreparedStatement

            try{
                ps = conn.prepareStatement(GET_PROJECT_INFO_QUERY)
                ps.use {
                    ps.setString(1,projectName)
                    val rs = ps.executeQuery()
                    rs.use {
                        while(rs.next()){
                            project.descr = rs.getString("projDescr")
                            project.initstate = rs.getString("projInitState")
                        }
                    }
                }

                ps = conn.prepareStatement(GET_PROJECT_LABELS_QUERY)
                ps.use {
                    ps.setString(1, projectName)
                    val rs = ps.executeQuery()
                    rs.use {
                        while(rs.next()){
                            project.labels.plus(rs.getString("labelName"))
                        }
                    }
                }

                ps = conn.prepareStatement(GET_PROJECT_STATES_QUERY)
                ps.use {
                    ps.setString(1, projectName)
                    val rs = ps.executeQuery()
                    rs.use {
                        while(rs.next()){
                            project.states.plus(rs.getString("stateName"))
                        }
                    }
                }

                ps = conn.prepareStatement(GET_PROJECT_STATE_TRANSITIONS_QUERY)
                ps.use {
                    ps.setString(1, projectName)
                    val rs = ps.executeQuery()
                    rs.use {
                        while(rs.next()){
                            project.transitions.plus(Pair(
                                    rs.getString("currState"),
                                    rs.getString("nextState")
                            ))
                        }
                    }
                }

            }catch ( ex : SQLException){

            } finally {
                conn.close()
            }

            return project
        }

    }

}