package isel.daw.DAW.Project.Comments

import isel.daw.DAW.Project.Common.COMMENTS_PATH
import isel.daw.DAW.Project.Common.CREATE_COMMENT_PATH
import isel.daw.DAW.Project.Common.DELETE_COMMENT_PATH
import isel.daw.DAW.Project.Common.GET_COMMENTS_PATH
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
        throw NotImplementedError()
    }

    /**
     * Endpoint to add a comment to an issue. Must receive the @param text:String.
     * POST /comments/createcomment/{tid} text={commenttext}
     */
    @PostMapping(CREATE_COMMENT_PATH)
    fun createComment(@PathVariable tid: Int, text: String) {
        throw NotImplementedError()
    }

    /**
     * Endpoint to delete a comment of an issue.
     * DELETE /comments/deletecomment/{cid}
     */
    @DeleteMapping(DELETE_COMMENT_PATH)
    fun deleteComment (@PathVariable cid: Int) {
        throw NotImplementedError()
    }
}