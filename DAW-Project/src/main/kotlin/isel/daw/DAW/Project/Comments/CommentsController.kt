package isel.daw.DAW.Project.Comments

import isel.daw.DAW.Project.Comments.CommentsDtos.*
import isel.daw.DAW.Project.Common.*
import org.springframework.web.bind.annotation.*

@RestController
class CommentsController(private val commentservices: CommentsServices) {
    /**
     * Endpoint to get all comments of an issue.
     * GET /comments{tid}
     */
    @AuthRequired
    @GetMapping(GET_COMMENTS_PATH)
    fun getComments(@PathVariable tid: Int): MutableList<SirenEntity<CommentsInfoOutputModel>> {
        val comments : MutableList<SirenEntity<CommentsInfoOutputModel>> = mutableListOf()
        commentservices.getComments(tid).forEach{
            comments.add(it.toSirenObject())
        }
        return comments
    }

    /**
     * Endpoint to add a comment to an issue. Must receive the @param text:String.
     * POST /comments/{tid}
     */
    @AuthRequired
    @PostMapping(CREATE_COMMENT_PATH)
    fun createComment(@PathVariable tid: Int, @RequestBody comment: CommentsInputModel): SirenEntity<CommentsCreatedResponse> {
        return commentservices.createComment(tid, comment).toSirenObject()
    }

    /**
     * Endpoint to update an issue comment. Must receive the @param text:String.
     * PUT /comments/{cid}
     */
    @AuthRequired
    @PutMapping(UPDATE_COMMENT_PATH)
    fun updateComment(@PathVariable cid: Int, @RequestBody comment: CommentsInputModel): SirenEntity<CommentsUpdatedResponse> {
        return commentservices.updateComment(cid, comment).toSirenObject()
    }

    /**
     * Endpoint to delete a comment of an issue.
     * DELETE /comments/{cid}
     */
    @AuthRequired
    @DeleteMapping(DELETE_COMMENT_PATH)
    fun deleteComment (@PathVariable cid: Int): SirenEntity<CommentsDeletedResponse> {
        return commentservices.deleteComment(cid).toSirenObject()
    }
}