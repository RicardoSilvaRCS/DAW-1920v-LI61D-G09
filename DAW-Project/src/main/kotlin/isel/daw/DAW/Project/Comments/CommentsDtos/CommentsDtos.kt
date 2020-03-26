package isel.daw.DAW.Project.Comments.CommentsDtos

import com.fasterxml.jackson.annotation.JsonCreator

/**
 *
 */
class CommentsInputModel @JsonCreator constructor(
        val text: String
)

/**
 *
 */
class CommentsOutputModel(val text: String, val creationdate: String)

/**
 *
 */
class CommentsInfoOutputModel(
        val id: Int,
        val taskid: Int,
        val text: String,
        val creationdate: String
)

/**
 * TODO:
 */
class InvalidCommentsException : Exception()