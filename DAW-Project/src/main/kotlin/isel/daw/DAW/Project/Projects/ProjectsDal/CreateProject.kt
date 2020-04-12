package isel.daw.DAW.Project.Projects.ProjectsDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInputModel
import java.sql.Connection
import java.sql.SQLException

/**
 *  TODO: We need to figure out what to return in this function.
 *
 *  TODO we need to make Transaction scopes
 */

class CreateProject {

    companion object {

        private const val INSERT_PROJECT : String  ="INSERT INTO public.project\n" +
                "\t (projname, projdescr, projinitstate)\n" +
                "\t VALUES (?,?, ?);"

        fun execute( newProject : ProjectsInputModel , conn : Connection) {

            try {
                conn.autoCommit = false
                val ps = conn.prepareStatement(INSERT_PROJECT)

                ps.use {
                    ps.setString(1, newProject.name)
                    ps.setString(2,newProject.descr)
                    ps.setString(3,newProject.initstate)
                    ps.execute()
                }

                insertLabels(newProject.name , newProject.labels , conn)
                insertStates(newProject.name , newProject.transitions , conn)
                insertTransitions(newProject.name , newProject.transitions , conn)
                conn.commit()
            } catch (ex: SQLException) {
                conn.rollback()
                throw InternalProcedureException("Error during project creation procedure." +
                        "Detailed problem: ${ex.message}")
            }finally {
                conn.close()
            }
        }

        //--------------------------------Insert Labels---------------------------------------//
        private const val INSERT_LABEL : String  = "INSERT INTO public.projectlabel\n" +
                "\t(labelname, projname)\n" +
                "\tVALUES (?,?); "

        fun insertLabels (projectName : String , labels : Array<String> , conn : Connection) {

            try {
                val ps = conn.prepareStatement(INSERT_LABEL)
                ps.use {
                    ps.setString(2, projectName)
                    for(currLabel in labels){
                        ps.setString(1,currLabel)
                        ps.execute()
                    }
                }
            } catch (ex: SQLException) {
                conn.rollback()
                throw InternalProcedureException("Error during project labels insertion procedure." +
                        "Detailed problem: ${ex.message}")
            }



        }

        //--------------------------------Insert States---------------------------------------//
        private const val INSERT_STATE : String  = "INSERT INTO public.projectstate\n" +
                "\t (statename, projname)\n" +
                "\t VALUES (?, ?);"

        private fun insertStates(projectName: String, transitions: Array<Pair<String, String>>, conn: Connection) {
            try {
                val states : Set<String> = getStates(transitions)

                val ps = conn.prepareStatement(INSERT_STATE)
                ps.use {
                    ps.setString(2, projectName)
                    for(currState in states){
                        ps.setString(1,currState)
                        ps.execute()
                    }
                }
            } catch (ex: SQLException) {
                conn.rollback()
                throw InternalProcedureException("Error during project states insertion procedure." +
                        "Detailed problem: ${ex.message}")
            }

        }

        private fun getStates (transitions: Array<Pair<String, String>>) : MutableSet<String> {
            val aux = mutableSetOf<String>()
            transitions.forEach {
                if(!aux.contains(it.first)){
                    aux.add(it.first)
                }
                if(!aux.contains(it.second)){
                    aux.add(it.second)
                }
            }
            return aux;
        }

        //--------------------------------Insert Transitions----------------------------------//
        private const val INSERT_TRANSITION: String  = "INSERT INTO public.statetransitions\n" +
                "\t (currstate, nextstate, projname)\n" +
                "\t VALUES (?, ?, ?);"

        private fun insertTransitions (projectName: String, transitions: Array<Pair<String, String>>, conn: Connection) {

            try {
                val ps = conn.prepareStatement(INSERT_TRANSITION)
                ps.use {
                    ps.setString(3, projectName)
                    for(currTran in transitions){
                        ps.setString(1, currTran.first)
                        ps.setString(2, currTran.second)
                        ps.execute()
                    }
                }
            } catch (ex: SQLException) {
                conn.rollback()
                throw InternalProcedureException("Error during project state-transitions insertion procedure." +
                        "Detailed problem: ${ex.message}")
            }

        }

    }
}