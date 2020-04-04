package isel.daw.DAW.Project.Comments

import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInputModel
import isel.daw.DAW.Project.Common.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(COMMENTS_PATH)
class CommentsController {
    /**
     * Endpoint to get all comments of an issue.
     * GET /comments/getcomments/{tid}
     */
    @GetMapping(GET_COMMENTS_PATH)
    fun getComments(@PathVariable tid: Int) {
        throw NotImplementedError("TODO!")
    }

    /**
     * Endpoint to add a comment to an issue. Must receive the @param text:String.
     * POST /comments/createcomment/{tid}
     */
    @PostMapping(CREATE_COMMENT_PATH)
    fun createComment(@PathVariable tid: Int, @RequestBody comment: CommentsInputModel) {
        throw NotImplementedError("TODO!")
    }

    /**
     * Endpoint to update an issue comment. Must receive the @param text:String.
     * PUT /comments/updatecomment/{tid}
     */
    @PutMapping(UPDATE_COMMENT_PATH)
    fun updateComment(@PathVariable tid: Int, @RequestBody comment: CommentsInputModel) {
        throw NotImplementedError("TODO!")
    }

    /**
     * Endpoint to delete a comment of an issue.
     * DELETE /comments/deletecomment/{cid}
     */
    @DeleteMapping(DELETE_COMMENT_PATH)
    fun deleteComment (@PathVariable cid: Int) {
        throw NotImplementedError("TODO!")
    }
}