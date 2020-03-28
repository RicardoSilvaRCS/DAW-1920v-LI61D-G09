package isel.daw.DAW.Project.Projects

import isel.daw.DAW.Project.Common.*
import isel.daw.DAW.Project.Projects.ProjectsDal.*
import isel.daw.DAW.Project.Projects.ProjectsDto.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import java.lang.Error
import java.util.*
import javax.sql.DataSource

/**
 * Repository for acessing Projects related data
 */

//We need to make here our validations
@Repository
@Component
class ProjectsRepository(@Autowired val dbs: DataSource) {

    fun getAll(): List<ProjectsOutputModel> {
        return GetProjects.execute(dbs.connection)
    }

    fun getByName( name: String ): ProjectsInfoOutputModel {
        return GetProject.execute(name, dbs.connection)
    }

    fun create( newProject: ProjectsInputModel) {
        //validateTrasitions(newProject)
        /**
         * Testing my, far superior, methods heheheh
         */
        val result1 = verifyGraph(
                graphInit(arrayOf( Pair("A","B") , Pair("B","A") , Pair("A","C") , Pair("C","closed") , Pair("closed","C") , Pair("closed","archived"))),
                "A"
        )

        val result2 = verifyGraph(
                graphInit(arrayOf( Pair("A","B") , Pair("A","C") , Pair("C","closed") , Pair("closed","C") , Pair("closed","archived"))),
                "A"
        )

        val result3 = verifyGraph(
                graphInit(arrayOf( Pair("A","B") , Pair("B","D"), Pair("D", "closed") , Pair("A","C") , Pair("A", "E"), Pair("E", "A"), Pair("C","closed") , Pair("closed","C") , Pair("closed", "D"), Pair("closed","archived"))),
                "A"
        )

        return CreateProject.execute(newProject, dbs.connection)
    }

    fun update( name: String, newProj: ProjectsUpdateInputModel ) {
        return UpdateProject.execute(name, newProj, dbs.connection)
    }

    fun delete( name: String ) {
        return DeleteProject.execute(name, dbs.connection)
    }

    companion object {

        private const val LAST_STATE : String = "archived"
        private const val BEFORE_LAST_STATE : String = "closed"

        fun validateTrasitions ( newProject : ProjectsInputModel) : Boolean{
            var teste : Array<Pair<String,String>> = arrayOf( Pair("A","B") , Pair("B","A") , Pair("A","C") , Pair("C","closed") , Pair("closed","C") , Pair("closed","archived"))
            val graph = createGraph(teste)
            //return verifyTransitions(newProject.initstate,graph)
            return verifyTransitions("A",graph)
        }

        fun createGraph (transitions : Array<Pair<String, String>> ) : MutableMap<String , TransitionNode> {
            var graph : MutableMap <String , TransitionNode> = mutableMapOf()

            transitions.forEach {
                var aux = graph.get(it.first)

                if(aux != null){
                    aux.nexts.add(TransitionNode(it.second))
                }else{
                    var auxNode = TransitionNode(it.first)
                    auxNode.nexts.add(TransitionNode(it.second))
                    graph[it.first] = auxNode
                }
            }
            return graph
        }

        fun verifyTransitions( currState : String , graph : MutableMap<String, TransitionNode>) : Boolean {
            var curr = graph[currState]

            for (it in curr!!.nexts){
                if(!it.active){

                    it.active = true
                    if(currState.equals(BEFORE_LAST_STATE)) {
                        if (it.equals(LAST_STATE)) {
                                return true
                        }
                    }
                    if(!verifyTransitions(it.curr,graph)){
                        return false
                    }
                }
            }
            return false
        }


        /**
         * Assim é que se faz pah!!! :
         */

        fun graphInit(transitions: Array<Pair<String, String>>): MutableMap<String , MutableList<String>> {
            val graph : MutableMap<String, MutableList<String>> = mutableMapOf()

            for(s in transitions){
                if(s.first == s.second) throw Error("Assim nao dá oh maninho, metes-me uma transiçao que vai para ela propria?!")
                if(!graph.containsKey(s.first)) {
                    graph.put(s.first, mutableListOf())
                }
                graph.get(s.first)?.add(s.second)
            }

            return graph
        }

        fun verifyGraph(graph : MutableMap<String , MutableList<String>>, INITIAL_STATE: String): Boolean{
            val initialNexts : MutableList<String> = graph.get(INITIAL_STATE)?:return false
            var PROGRESSO: String = "POR AQUI N SEI BEM FDZ"
            for( s in initialNexts ) {
                var aux = verifyAux(INITIAL_STATE, s, graph)
                if(aux == "TOU PRESO AQUI OH MANO") return false
                if(PROGRESSO == "POR AQUI N SEI BEM FDZ") {
                    PROGRESSO = aux
                }
            }
            when(PROGRESSO) {
                "POR AQUI N SEI BEM FDZ" -> return false
                "AQUI DA CRLH" -> return true
                else -> return false
            }
        }

        fun verifyAux(called: String, toCheck: String, graph: MutableMap<String, MutableList<String>>) : String{
            val nexts: List<String> = graph.get(toCheck)?: return "TOU PRESO AQUI OH MANO"
            var PROGRESSO = "POR AQUI N SEI BEM FDZ"
            if(nexts.isEmpty()) return "TOU PRESO AQUI OH MANO"
            for( s in nexts ) {
                when(s) {
                    called -> PROGRESSO = "POR AQUI N SEI BEM FDZ"
                    "archived" -> PROGRESSO = "AQUI DA CRLH"
                    else -> {
                        val aux = verifyAux(toCheck, s, graph)
                        if(aux == "TOU PRESO AQUI OH MANO") {
                            return "TOU PRESO AQUI OH MANO"
                        }
                        if(PROGRESSO == "POR AQUI N SEI BEM FDZ") {
                            PROGRESSO = aux
                        }
                    }
                }
            }
            return PROGRESSO
        }
    }
}