package isel.daw.DAW.Project.Comments

import isel.daw.DAW.Project.Comments.CommentsDtos.*
import isel.daw.DAW.Project.Common.InvalidCommentException
import isel.daw.DAW.Project.Common.InvalidResourceRequestedException
import isel.daw.DAW.Project.Common.NoCommentsFoundError
import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInfoOutputModel
import isel.daw.DAW.Project.Issues.IssuesRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Services layer for Comments related operations. All the business logic is implemented in this layer.
 */

@Component
@Service
class CommentsServices(private val commentsrepo: CommentsRepository, private val issuesrepo: IssuesRepository) {

    fun getComments(tid: Int): List<CommentsInfoOutputModel> {
        if(tid<0) {
            throw InvalidResourceRequestedException("Issue id received is invalid, can't be negative.")
        }
        val foundComments = commentsrepo.getCommentsOfIssue(tid)
        if(foundComments.isEmpty()) {
            throw NoCommentsFoundError("No comments found in issue w/ id: $tid")
        }
        return commentsrepo.getCommentsOfIssue(tid)
    }

    fun createComment(tid: Int, comment: CommentsInputModel): CommentsCreatedResponse {
        if(tid<0) {
            throw InvalidResourceRequestedException("Issue id received is invalid, can't be negative.")
        }
        if(!comment.isValid()) {
            throw InvalidCommentException("No text was inserted for the creation of a new comment.")
        }
        val issueOfComment: IssuesInfoOutputModel = issuesrepo.getById(tid)
        if(!issueOfComment.isDefault()) {
            throw InvalidResourceRequestedException("There is no issue with id '$tid'.")
        }
        return commentsrepo.addToIssue(tid, comment)
    }

    fun updateComment(cid: Int, comment: CommentsInputModel): CommentsUpdatedResponse {
        if(cid<0) {
            throw InvalidResourceRequestedException("Comment id received is invalid, can't be negative.")
        }
        return commentsrepo.update(cid, comment)
    }

    fun deleteComment(cid: Int): CommentsDeletedResponse {
        if(cid<0) {
            throw InvalidResourceRequestedException("Comment id received is invalid, can't be negative.")
        }
        return commentsrepo.deleteFromIssue(cid)
    }

}