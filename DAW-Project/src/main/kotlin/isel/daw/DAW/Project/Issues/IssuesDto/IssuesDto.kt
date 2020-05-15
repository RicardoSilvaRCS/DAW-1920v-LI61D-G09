package isel.daw.DAW.Project.Issues.IssuesDto

import com.fasterxml.jackson.annotation.JsonCreator
import isel.daw.DAW.Project.Common.*
import isel.daw.DAW.Project.Projects.ProjectsDto.CREATE_PROJECT_ACTION
import isel.daw.DAW.Project.Projects.ProjectsDto.DELETE_PROJECT_ACTION
import isel.daw.DAW.Project.Projects.ProjectsDto.GET_PROJECTS_ACTION
import isel.daw.DAW.Project.Projects.ProjectsDto.UPDATE_PROJECT_ACTION
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.net.URI
import java.sql.Timestamp

/**---------------------------------------------------INPUT DTOS--------------------------------------------*/
/**
 * Data model for the creation of a new Issue
 */
class IssuesInputModel @JsonCreator constructor(
        val name: String,
        val projname: String,
        val descr: String,
        val currState: String,
        val labels: Array<String>
) {
    fun isValid(): Boolean {
        return !(name.isEmpty() || name.isBlank()) && !(projname.isEmpty() || name.isBlank()) && !(currState.isEmpty() || currState.isBlank())
    }
}

/**
 * Data model for the update state of an Issue
 */
class IssuesStateInputModel(val state: String) {
    fun isValid(): Boolean {
        return !(state.isEmpty() || state.isBlank())
    }
}

/**
 * Data model for the creation of a new Issue Label
 */
class IssuesLabelInputModel @JsonCreator constructor(
        val labelName: String,
        val projname: String
) {
    fun isValid(): Boolean {
        return !(labelName.isEmpty() || labelName.isBlank()) && projname.isNotEmpty()
    }
}

/**---------------------------------------------------OUTPUT DTOS--------------------------------------------*/
/**
 * Data model for the representation of an Issue
 */
class IssuesOutputModel(val name: String, val descr: String, val state: String , val id : Int){
    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("IssuesInfo"),
            links = listOf(
                    SirenLink(rel = listOf("issue-comments"), href = URI(GET_COMMENTS_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("update-issue"), href = URI(UPDATE_ISSUE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("update-issue-state"), href = URI(UPDATE_ISSUE_STATE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("delete-issue"), href = URI(DELETE_ISSUE_PATH.replace("{tid}",this.id.toString())))

            ),
            actions = listOf(GET_SINGLE_ISSUE_ACTION, CREATE_ISSUE_ACTION, UPDATE_ISSUE_ACTION, UPDATE_ISSUE_STATE_ACTION, DELETE_ISSUE_ACTION, CREATE_ISSUE_LABEL_ACTION, DELETE_ISSUE_LABEL_ACTION)
    )
}

/**
 * Data model for the full detailed representation of an Issue
 */
class IssuesInfoOutputModel(
        var id: Int = -1,
        var name: String = "",
        var projname: String = "",
        var descr: String = "",
        val labels: MutableList<String> = arrayListOf(),
        var currState: String = "",
        var possibleNextStates : MutableList<String> = mutableListOf(),
        var creationDate: Timestamp? = null,
        var updateDate: Timestamp? = null,
        var closeDate: Timestamp? = null
){
    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("IssueInfo"),
            links = listOf(
                    SirenLink(rel = listOf("project-issue-owner"), href = URI(GET_SINGLE_PROJECT_PATH.replace("{pname}",this.projname))),
                    SirenLink(rel = listOf("issue-comments"), href = URI(GET_COMMENTS_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("update-issue"), href = URI(UPDATE_ISSUE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("update-issue-state"), href = URI(UPDATE_ISSUE_STATE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("delete-issue"), href = URI(DELETE_ISSUE_PATH.replace("{tid}",this.id.toString())))
            ),
            actions = listOf(GET_SINGLE_ISSUE_ACTION, CREATE_ISSUE_ACTION, UPDATE_ISSUE_ACTION, UPDATE_ISSUE_STATE_ACTION, DELETE_ISSUE_ACTION, CREATE_ISSUE_LABEL_ACTION, DELETE_ISSUE_LABEL_ACTION)
    )

    /**
     * This function might be used to check if a request to GET issue returned any issue or not.
     * For now it's here but it might be a little PG
     * Only checks if the is -1 because if it is we know the request did not found an issue.
     */
    fun isDefault(): Boolean {
        return id < 0
    }
}

/**
 * Data model returned when an Issue is successfully created
 */
class IssueCreationResponse(
        val id: Int,
        val projname: String,
        val name: String
){
    val message: String = "Issue created with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.CREATED

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("IssueCreation"),
            links = listOf(
                    SirenLink(rel = listOf("project-issue-owner"), href = URI(GET_SINGLE_PROJECT_PATH.replace("{pname}",this.projname))),
                    SirenLink(rel = listOf("issue-comments"), href = URI(GET_COMMENTS_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("get-issue"), href = URI(GET_SINGLE_ISSUE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("update-issue"), href = URI(UPDATE_ISSUE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("update-issue-state"), href = URI(UPDATE_ISSUE_STATE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("delete-issue"), href = URI(DELETE_ISSUE_PATH.replace("{tid}",this.id.toString())))
            ),
            actions = listOf(GET_SINGLE_ISSUE_ACTION, CREATE_ISSUE_ACTION, UPDATE_ISSUE_ACTION, UPDATE_ISSUE_STATE_ACTION, DELETE_ISSUE_ACTION, CREATE_ISSUE_LABEL_ACTION, DELETE_ISSUE_LABEL_ACTION)
    )
}

/**
 * Data model returned when an Issue is successfully updated
 */
class IssueUpdatedResponse(
        val id: Int,
        val projname: String
){
    val message: String = "Issue updated with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("IssueUpdated"),
            links = listOf(
                    SirenLink(rel = listOf("project-issue-owner"), href = URI(GET_SINGLE_PROJECT_PATH.replace("{pname}",this.projname))),
                    SirenLink(rel = listOf("issue-comments"), href = URI(GET_COMMENTS_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("get-issue"), href = URI(GET_SINGLE_ISSUE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("update-issue"), href = URI(UPDATE_ISSUE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("delete-issue"), href = URI(DELETE_ISSUE_PATH.replace("{tid}",this.id.toString())))
            ),
            actions = listOf(GET_SINGLE_ISSUE_ACTION, CREATE_ISSUE_ACTION, UPDATE_ISSUE_ACTION, UPDATE_ISSUE_STATE_ACTION, DELETE_ISSUE_ACTION, CREATE_ISSUE_LABEL_ACTION, DELETE_ISSUE_LABEL_ACTION)
    )
}

/**
 * Data model returned when an Issue's state is successfully updated
 */
class IssueStateUpdatedResponse(
        val id: Int
){
    val message: String = "Issue state updated with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("IssueStateUpdated"),
            links = listOf(
                    SirenLink(rel = listOf("issue-comments"), href = URI(GET_COMMENTS_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("get-issue"), href = URI(GET_SINGLE_ISSUE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("update-issue-state"), href = URI(UPDATE_ISSUE_STATE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("delete-issue"), href = URI(DELETE_ISSUE_PATH.replace("{tid}",this.id.toString())))
            ),
            actions = listOf(GET_SINGLE_ISSUE_ACTION, CREATE_ISSUE_ACTION, UPDATE_ISSUE_ACTION, UPDATE_ISSUE_STATE_ACTION, DELETE_ISSUE_ACTION, CREATE_ISSUE_LABEL_ACTION, DELETE_ISSUE_LABEL_ACTION)
    )
}

/**
 * Data model returned when an Issue is successfully deleted
 */
class IssueDeletedResponse{
    val message: String = "Issue deleted with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("IssueUpdated"),
            links = listOf(
                    SirenLink(rel = listOf("get-projects"), href = URI(GET_PROJECTS_PATH.replace("{",":").replace("}","")))
            ),
            actions = listOf(GET_SINGLE_ISSUE_ACTION, CREATE_ISSUE_ACTION, UPDATE_ISSUE_ACTION, UPDATE_ISSUE_STATE_ACTION, DELETE_ISSUE_ACTION)
    )
}
/**
 * Data model returned when an Issue label is successfully created
 */
class IssueLabelCreationResponse(
        val id: Int
){
    val message: String = "Issue label created with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.CREATED

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("IssueLabelCreated"),
            links = listOf(
                    SirenLink(rel = listOf("issue-comments"), href = URI(GET_COMMENTS_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("get-issue"), href = URI(GET_SINGLE_ISSUE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("update-issue-state"), href = URI(UPDATE_ISSUE_STATE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("delete-issue"), href = URI(DELETE_ISSUE_PATH.replace("{tid}",this.id.toString())))
            ),
            actions = listOf(GET_SINGLE_ISSUE_ACTION, CREATE_ISSUE_ACTION, UPDATE_ISSUE_ACTION, UPDATE_ISSUE_STATE_ACTION, DELETE_ISSUE_ACTION, DELETE_ISSUE_LABEL_ACTION)
    )
}

/**
 * Data model returned when an Issue label is successfully removed
 */
class IssueLabelDeletedResponse(
        val id: Int
){
    val message: String = "Issue label deleted with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("IssueLabelDeleted"),
            links = listOf(
                    SirenLink(rel = listOf("issue-comments"), href = URI(GET_COMMENTS_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("get-issue"), href = URI(GET_SINGLE_ISSUE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("update-issue-state"), href = URI(UPDATE_ISSUE_STATE_PATH.replace("{tid}",this.id.toString()))),
                    SirenLink(rel = listOf("delete-issue"), href = URI(DELETE_ISSUE_PATH.replace("{tid}",this.id.toString())))
            ),
            actions = listOf(GET_SINGLE_ISSUE_ACTION, CREATE_ISSUE_ACTION, UPDATE_ISSUE_ACTION, UPDATE_ISSUE_STATE_ACTION, DELETE_ISSUE_ACTION, CREATE_ISSUE_LABEL_ACTION)
    )
}

/**---------------------------SIREN ACTIONS------------------------------------------*/
/**This describe the possible actions*/

val GET_ISSUES_ACTION = SirenAction(
        name = "get-issues",
        title = "Get the issue associated to specified project name",
        href = URI(ISSUES_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.GET
)

val GET_SINGLE_ISSUE_ACTION = SirenAction(
        name = "get-single-issue",
        title = "Get issue info",
        href = URI(GET_SINGLE_ISSUE_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.GET,
        fields = listOf(SirenAction.Field("tid" , "number"))
)

val CREATE_ISSUE_ACTION = SirenAction(
        name = "create-issue",
        title = "Create new issue",
        href = URI(CREATE_ISSUE_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.POST,
        type = MediaType.APPLICATION_JSON
)

val UPDATE_ISSUE_ACTION = SirenAction(
        name = "update-issue-info",
        title = "Update the specified issue info",
        href = URI(UPDATE_ISSUE_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.PUT,
        type = MediaType.APPLICATION_JSON,
        fields = listOf(SirenAction.Field("tid" , "number"))
)

val UPDATE_ISSUE_STATE_ACTION = SirenAction(
        name = "update-issue-state",
        title = "Update the specified issue state",
        href = URI(UPDATE_ISSUE_STATE_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.PUT,
        type = MediaType.APPLICATION_JSON,
        fields = listOf(SirenAction.Field("tid" , "number"))
)

val DELETE_ISSUE_ACTION = SirenAction(
        name = "delete-issue",
        title = "Delete the specified issue",
        href = URI(DELETE_ISSUE_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.DELETE,
        fields = listOf(SirenAction.Field("tid" , "number"))
)

val CREATE_ISSUE_LABEL_ACTION = SirenAction(
        name = "create-issue-label",
        title = "Create new issue label",
        href = URI(CREATE_ISSUE_LABEL_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.POST,
        type = MediaType.APPLICATION_JSON,
        fields = listOf(SirenAction.Field("tid" , "number"))
)

val DELETE_ISSUE_LABEL_ACTION = SirenAction(
        name = "delete-issue-label",
        title = "Delete an issue label",
        href = URI(DELETE_ISSUE_LABEL_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.DELETE,
        fields = listOf(
                SirenAction.Field("tid" , "number"),
                SirenAction.Field("labelname" , "string")
        )
)


