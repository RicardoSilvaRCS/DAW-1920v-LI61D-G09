package isel.daw.DAW.Project.Projects.ProjectsDto

import com.fasterxml.jackson.annotation.JsonCreator
import isel.daw.DAW.Project.Common.*
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.net.URI
import java.sql.Timestamp

/**---------------------------------------------------INPUT DTOS--------------------------------------------*/

/**
 *  Data model for the creation of a new Project
 */

class ProjectsInputModel @JsonCreator constructor(
        val name: String,
        val descr: String,
        val labels: Array<String>,
        val initstate: String,
        val transitions: Array<Pair<String, String>>) {
    /**
     * Changed this verification code here for now because it's a better place than DAL layer or Repository
     * Its functionality is limited by the existence of this object, soo i think its not that bad of an option
     *
     * TODO: I'm still not 100% sure of the functionality of this algorithm, i did a few tests soo it works so far,
     *       We need to test this in a more detailed manner, and maybe redo the code, it might not be that pretty.
     */

    companion object {

        fun transGraphInit(transitions: Array<Pair<String, String>>): MutableMap<String , MutableList<String>> {
            val graph : MutableMap<String, MutableList<String>> = mutableMapOf()
            var haveClosedArch: Boolean = false
            for(s in transitions){
                if(s.first == s.second) throw InvalidStateTransitionException("Can't have a transition for itself.")
                if(s.first == CLOSED_ARCH_TRANS.first && s.second == CLOSED_ARCH_TRANS.second) haveClosedArch = true
                if(!graph.containsKey(s.first)) {
                    graph.put(s.first, mutableListOf())
                }
                graph.get(s.first)?.add(s.second)
            }
            if(!haveClosedArch) throw InvalidStateTransitionException("Transition Closed->Archived not found.")
            return graph
        }

        fun transVerify(graph : MutableMap<String , MutableList<String>>, INITIAL_STATE: String): Boolean{
            val initialNexts : MutableList<String> = graph.get(INITIAL_STATE)?:return false
            var transVerificationProgress: Int = 1
            for( s in initialNexts ) {
                var branchVerify = transVerifyAux(INITIAL_STATE, s, graph)
                if(branchVerify == 0) return false
                if(transVerificationProgress == 1) {
                    transVerificationProgress = branchVerify
                }
            }
            when(transVerificationProgress) {
                1 -> return false
                2 -> return true
                else -> return false
            }
        }

        fun transVerifyAux(called: String, toCheck: String, graph: MutableMap<String, MutableList<String>>) : Int{
            val nexts: List<String> = graph.get(toCheck)?: return 0
            var nodeProgress = 1
            if(nexts.isEmpty()) return 0
            for( s in nexts ) {
                when(s) {
                    called -> nodeProgress = 1
                    FINAL_STATE -> nodeProgress = 2
                    else -> {
                        val nodeVerify = transVerifyAux(toCheck, s, graph)
                        if(nodeVerify == 0) {
                            return 0
                        }
                        if(nodeProgress == 1) {
                            nodeProgress = nodeVerify
                        }
                    }
                }
            }
            return nodeProgress
        }

        fun isUsable(s: String?): Boolean {
            return !(s.isNullOrEmpty() || s.isBlank())
        }
    }

    fun verifyTransitions(): Boolean {
        return transVerify(transGraphInit(transitions), initstate)
    }

    fun isValid(): Boolean {
        return isUsable(this.name) && isUsable(this.initstate) && this.verifyTransitions()
    }
}

val CLOSED_ARCH_TRANS: Pair<String,String> = Pair("Closed", "Archived")
val FINAL_STATE: String = "Archived"

/**
 *  Data model for the representation of a projects info to update
 */
class ProjectsUpdateInputModel @JsonCreator constructor(val newDescr: String)

/**---------------------------------------------------OUTPUT DTOS--------------------------------------------*/

/**
 *  Data model for the representation of a Project
 */
class ProjectsOutputModel(val name: String, val descr: String){

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("ProjectsInfo"),
            links = listOf(
                    SirenLink(rel = listOf("project-detailed-info"), href = URI(GET_SINGLE_PROJECT_PATH.replace("{pname}",this.name))),
                    SirenLink(rel = listOf("project-issues"), href = URI(GET_ISSUES_PATH.replace("{pname}",this.name)))
            ),
            actions = listOf(GET_SINGLEPROJECT_ACTION, CREATE_PROJECT_ACTION, UPDATE_PROJECT_ACTION, DELETE_PROJECT_ACTION)
    )
}


/**
 * Data model for the full detailed representation of a Project
 */
class ProjectsInfoOutputModel(
        var name: String = "",
        var descr: String? = "",
        var labels: MutableList<String> = arrayListOf(),
        var initstate: String? = "",
        var states: MutableList<String> = arrayListOf(),
        var transitions: MutableList<Pair<String, String>> = arrayListOf()
){
    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("ProjectsInfo"),
            links = listOf(
                    SirenLink(rel = listOf("project-issues"), href = URI(GET_ISSUES_PATH.replace("{pname}",this.name))),
                    SirenLink(rel = listOf("update-project"), href = URI(UPDATE_PROJECT_PATH.replace("{pname}",this.name))),
                    SirenLink(rel = listOf("delete-project"), href = URI(DELETE_PROJECT_PATH.replace("{pname}",this.name)))
            ),
            actions = listOf(GET_PROJECTS_ACTION, CREATE_PROJECT_ACTION, UPDATE_PROJECT_ACTION, DELETE_PROJECT_ACTION)
    )

    /**
     * This function might be used to check if a request to GET projects returned any project or not.
     * For now it's here but it might be a little PG
     * Only checks if the name is empty because if it is we know the request did not found a project-
     */
    fun isDefault(): Boolean {
        return name.isEmpty()
    }
}

/**
 * Data model returned when a Project is successfully created
 */
class ProjectCreationResponse(
        val name: String,
        val userName : String
){
    val message: String = "Project created with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.CREATED

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("ProjectCreation"),
            links = listOf(
                    SirenLink(rel = listOf("get-project"), href = URI(GET_SINGLE_PROJECT_PATH.replace("{pname}", name))),
                    SirenLink(rel = listOf("update-project"), href = URI(UPDATE_PROJECT_PATH.replace("{pname}", name))),
                    SirenLink(rel = listOf("delete-project"), href = URI(DELETE_PROJECT_PATH.replace("{pname}", name))),
                    SirenLink(rel = listOf("project-issues"), href = URI(GET_ISSUES_PATH.replace("{pname}", name))),
                    SirenLink(rel = listOf("project-issues"), href = URI(GET_USER_INFO.replace("{userName}", userName)))
            ),
            actions = listOf(GET_PROJECTS_ACTION, CREATE_PROJECT_ACTION, UPDATE_PROJECT_ACTION, DELETE_PROJECT_ACTION, CREATE_PROJECT_LABEL_ACTION, DELETE_PROJECT_LABEL_ACTION)
    )
}

/**
 * Data model returned when a Project is successfully updated
 */
class ProjectUpdatedResponse(
        val name: String
){
    val message: String = "Project updated with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("ProjectUpdated"),
            links = listOf(
                    SirenLink(rel = listOf("get-project"), href = URI(GET_SINGLE_PROJECT_PATH.replace("{pname}", name))),
                    SirenLink(rel = listOf("delete-project"), href = URI(DELETE_PROJECT_PATH.replace("{pname}", name))),
                    SirenLink(rel = listOf("project-issues"), href = URI(GET_ISSUES_PATH.replace("{pname}", name)))
            ),
            actions = listOf(GET_PROJECTS_ACTION, CREATE_PROJECT_ACTION, UPDATE_PROJECT_ACTION, DELETE_PROJECT_ACTION, CREATE_PROJECT_LABEL_ACTION, DELETE_PROJECT_LABEL_ACTION)
    )
}

/**
 * Data model returned when a Project is successfully deleted
 */
class ProjectDeletedResponse(){
    val message: String = "Project deleted with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("ProjectDeleted"),
            links = listOf(
                    SirenLink(rel = listOf("create-project"), href = URI(CREATE_PROJECT_PATH.replace("{userName}", "userName")))
            ),
            actions = listOf(CREATE_PROJECT_ACTION)
    )
}

/**
 * Data model returned when a Project label is successfully created
 */
class ProjectLabelCreationResponse(
        val name: String
){
    val message: String = "Project label created with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.CREATED

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("ProjectLabelCreated"),
            links = listOf(
                    SirenLink(rel = listOf("delete-project-label"), href = URI(DELETE_PROJECT_LABEL_PATH.replace("{",":").replace("}",""))),
                    SirenLink(rel = listOf("get-project"), href = URI(GET_SINGLE_PROJECT_PATH.replace("{pname}", name))),
                    SirenLink(rel = listOf("delete-project"), href = URI(DELETE_PROJECT_PATH.replace("{pname}", name))),
                    SirenLink(rel = listOf("project-issues"), href = URI(GET_ISSUES_PATH.replace("{pname}", name)))
            ),
            actions = listOf(GET_PROJECTS_ACTION, CREATE_PROJECT_ACTION, UPDATE_PROJECT_ACTION, DELETE_PROJECT_ACTION, DELETE_PROJECT_LABEL_ACTION)
    )
}

/**
 * Data model returned when a Project label is successfully deleted
 */
class ProjectLabelDeleteResponse(
        val name: String
){
    val message: String = "Project label deleted with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("ProjectLabelDeleted"),
            links = listOf(
                    SirenLink(rel = listOf("create-project-label"), href = URI(CREATE_PROJECT_LABEL_PATH.replace("{",":").replace("}",""))),
                    SirenLink(rel = listOf("get-project"), href = URI(GET_SINGLE_PROJECT_PATH.replace("{pname}", name))),
                    SirenLink(rel = listOf("delete-project"), href = URI(DELETE_PROJECT_PATH.replace("{pname}", name))),
                    SirenLink(rel = listOf("project-issues"), href = URI(GET_ISSUES_PATH.replace("{pname}", name)))
            ),
            actions = listOf(GET_PROJECTS_ACTION, CREATE_PROJECT_ACTION, UPDATE_PROJECT_ACTION, DELETE_PROJECT_ACTION, CREATE_PROJECT_LABEL_ACTION)
    )
}

/**---------------------------SIREN ACTIONS------------------------------------------*/

/**This describe the possible actions*/

val GET_PROJECTS_ACTION = SirenAction(
        name = "get-project",
        title = "Get the project with the pname",
        href = URI(GET_PROJECTS_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.GET
)


val GET_SINGLEPROJECT_ACTION = SirenAction(
        name = "get-project",
        title = "Get the specified project",
        href = URI(PROJECTS_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.GET,
        fields = listOf(SirenAction.Field("pname" , "string"))
)


val CREATE_PROJECT_ACTION = SirenAction(
        name = "create-new-project",
        title = "Create a new project",
        href = URI(CREATE_PROJECT_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.POST,
        type = MediaType.APPLICATION_JSON
)

val UPDATE_PROJECT_ACTION = SirenAction(
        name = "update-new-project",
        title = "Update the project info",
        href = URI(UPDATE_PROJECT_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.PUT,
        type = MediaType.APPLICATION_JSON,
        fields = listOf(SirenAction.Field("pname" , "string"))
)

val DELETE_PROJECT_ACTION = SirenAction(
        name = "delete-project",
        title = "Delete specified project",
        href = URI(DELETE_PROJECT_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.DELETE,
        fields = listOf(SirenAction.Field("pname" , "string"))
)

val CREATE_PROJECT_LABEL_ACTION = SirenAction(
        name = "create-new-project-label",
        title = "Create a new project label",
        href = URI(CREATE_PROJECT_LABEL_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.POST,
        type = MediaType.APPLICATION_JSON,
        fields = listOf(
                SirenAction.Field("pname" , "string"),
                SirenAction.Field("labelname" , "string")
        )
)

val DELETE_PROJECT_LABEL_ACTION = SirenAction(
        name = "delete-project-label",
        title = "Delete a project label",
        href = URI(DELETE_PROJECT_LABEL_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.DELETE,
        fields = listOf(
                SirenAction.Field("pname" , "string"),
                SirenAction.Field("labelname" , "string")
        )
)




