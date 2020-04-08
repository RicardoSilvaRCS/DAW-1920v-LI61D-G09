package isel.daw.DAW.Project.Comments

import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInfoOutputModel
import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInputModel
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Services layer for Comments related operations. All the business logic is implemented in this layer.
 *
 * TODO: We need to make our validations.
 */

@Component
@Service
class CommentsServices(private val commentsrepo: CommentsRepository) {

    fun getComments(tid: Int): List<CommentsInfoOutputModel> {
        return commentsrepo.getCommentsOfIssue(tid)
    }

    fun createComment(tid: Int, comment: CommentsInputModel) {
        return commentsrepo.addToIssue(tid, comment)
    }

    fun updateComment(tid: Int, comment: CommentsInputModel) {
        return commentsrepo.update(tid, comment)
    }

    fun deleteComment(cid: Int) {
        return commentsrepo.deleteFromIssue(cid)
    }

}