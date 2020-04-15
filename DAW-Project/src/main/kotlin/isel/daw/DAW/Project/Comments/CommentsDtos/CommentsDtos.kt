package isel.daw.DAW.Project.Comments.CommentsDtos

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
import java.util.*

/**---------------------------------------------------INPUT DTOS--------------------------------------------*/

/**
 *Data model for the creation of a new Comment
 */
class CommentsInputModel @JsonCreator constructor(
        val text: String
) {
    fun isValid(): Boolean {
        return !(text.isEmpty() || text.isBlank())
    }
}


/**---------------------------------------------------OUTPUT DTOS--------------------------------------------*/

/**
 * Data model for the representation of an Comment
 */
class CommentsOutputModel(val text: String, val creationdate: String){
    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("IssueInfo"),
            links = listOf(),
            actions = listOf()
    )
}

/**
 * Data model for the full detailed representation of an Comment
 */
class CommentsInfoOutputModel(
        val id: Int,
        val taskid: Int,
        val text: String,
        val creationdate: Timestamp
){
    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("IssueInfo"),
            links = listOf(
                    SirenLink(rel = listOf("issue-comment-owner"), href = URI(GET_SINGLE_ISSUE_PATH.replace("{tid}",this.taskid.toString()))),
                    SirenLink(rel = listOf("update-comment"), href = URI(UPDATE_COMMENT_PATH.replace("{cid}",this.taskid.toString()))),
                    SirenLink(rel = listOf("delete-comment"), href = URI(DELETE_COMMENT_PATH.replace("{cid}",this.taskid.toString())))
            ),
            actions = listOf(GET_SINGLE_COMMENT_ACTION, CREATE_COMMENT_ACTION, UPDATE_COMMENT_ACTION, DELETE_COMMENT_ACTION)
    )
}

/**
 * Data model returned when a Comment is successfully created
 */
class CommentsCreatedResponse(
        val tid: Int,
        val cid: Int
){
    val message: String = "Comment created with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.CREATED

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("CommentCreation"),
            links = listOf(
                    SirenLink(rel = listOf("issue-comment-owner"), href = URI(GET_SINGLE_ISSUE_PATH.replace("{tid}",this.tid.toString()))),
                    SirenLink(rel = listOf("update-comment"), href = URI(UPDATE_COMMENT_PATH.replace("{cid}",this.cid.toString()))),
                    SirenLink(rel = listOf("delete-comment"), href = URI(DELETE_COMMENT_PATH.replace("{cid}",this.cid.toString())))
            ),
            actions = listOf(GET_SINGLE_COMMENT_ACTION, CREATE_COMMENT_ACTION, UPDATE_COMMENT_ACTION, DELETE_COMMENT_ACTION)
    )
}

/**
 * Data model returned when a Comment is successfully updated
 */
class CommentsUpdatedResponse(
        val cid: Int
){
    val message: String = "Comment updated with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("CommentUpdated"),
            links = listOf(
                    SirenLink(rel = listOf("issue-comment-owner"), href = URI(GET_SINGLE_ISSUE_PATH.replace("{tid}",this.cid.toString()))),
                    SirenLink(rel = listOf("delete-comment"), href = URI(DELETE_COMMENT_PATH.replace("{cid}",this.cid.toString())))
            ),
            actions = listOf(GET_SINGLE_COMMENT_ACTION, CREATE_COMMENT_ACTION, UPDATE_COMMENT_ACTION, DELETE_COMMENT_ACTION)
    )
}


/**
 * Data model returned when a Comment is successfully deleted
 */
class CommentsDeletedResponse(
        val cid: Int
){
    val message: String = "Comment deleted with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("CommentDeleted"),
            links = listOf(),
            actions = listOf(GET_SINGLE_COMMENT_ACTION, CREATE_COMMENT_ACTION, UPDATE_COMMENT_ACTION, DELETE_COMMENT_ACTION)
    )
}


/**---------------------------SIREN ACTIONS------------------------------------------*/
/**This describe the possible actions*/


val GET_COMMENTS_ACTIOn = SirenAction(
        name = "get-issues",
        title = "Get the issue associated to specified project name",
        href = URI(COMMENTS_PATH),
        method = HttpMethod.GET
)

val GET_SINGLE_COMMENT_ACTION = SirenAction(
        name = "get-single-issue",
        title = "Get issue info",
        href = URI(COMMENTS_PATH),
        method = HttpMethod.GET,
        fields = listOf(SirenAction.Field("tid" , "number"))
)

val CREATE_COMMENT_ACTION = SirenAction(
        name = "create-issue",
        title = "Create new issue",
        href = URI(COMMENTS_PATH),
        method = HttpMethod.POST,
        type = MediaType.APPLICATION_JSON
)

val UPDATE_COMMENT_ACTION = SirenAction(
        name = "update-issue-info",
        title = "Update the specified issue info",
        href = URI(COMMENTS_PATH),
        method = HttpMethod.PUT,
        type = MediaType.APPLICATION_JSON,
        fields = listOf(SirenAction.Field("tid" , "number"))
)

val DELETE_COMMENT_ACTION = SirenAction(
        name = "delete-issue",
        title = "Delete the specified issue",
        href = URI(COMMENTS_PATH),
        method = HttpMethod.DELETE,
        fields = listOf(SirenAction.Field("cid" , "number"))
)