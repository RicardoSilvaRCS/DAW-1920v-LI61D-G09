package isel.daw.DAW.Project.Comments

import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInfoOutputModel
import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInputModel
import isel.daw.DAW.Project.Common.*
import org.springframework.web.bind.annotation.*

@RestController
class CommentsController(private val commentservices: CommentsServices) {
    /**
     * Endpoint to get all comments of an issue.
     * GET /comments{tid}
     */
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
    @PostMapping(CREATE_COMMENT_PATH)
    fun createComment(@PathVariable tid: Int, @RequestBody comment: CommentsInputModel) {
        return commentservices.createComment(tid, comment)
    }

    /**
     * Endpoint to update an issue comment. Must receive the @param text:String.
     * PUT /comments/{tid}
     */
    @PutMapping(UPDATE_COMMENT_PATH)
    fun updateComment(@PathVariable cid: Int, @RequestBody comment: CommentsInputModel) {
        return commentservices.updateComment(cid, comment)
    }

    /**
     * Endpoint to delete a comment of an issue.
     * DELETE /comments/{cid}
     */
    @DeleteMapping(DELETE_COMMENT_PATH)
    fun deleteComment (@PathVariable cid: Int) {
        return commentservices.deleteComment(cid)
    }
}