package isel.daw.DAW.Project.Comments

import isel.daw.DAW.Project.Comments.CommentsDal.DeleteComment
import isel.daw.DAW.Project.Comments.CommentsDal.GetIssueComments
import isel.daw.DAW.Project.Comments.CommentsDal.InsertComment
import isel.daw.DAW.Project.Comments.CommentsDal.UpdateComment
import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInfoOutputModel
import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInputModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.sql.DataSource
/**
 * Repository for acessing Comments related data
 */
@Component
@Repository
class CommentsRepository(@Autowired val dbs: DataSource) {

    fun getCommentsOfIssue(tid: Int): List<CommentsInfoOutputModel> {
        return GetIssueComments.execute(tid, dbs.connection)
    }

    fun addToIssue(tid: Int, comment: CommentsInputModel) {
        return InsertComment.execute(tid, comment, dbs.connection)
    }

    fun update(cid: Int, comment: CommentsInputModel) {
        return UpdateComment.execute(cid, comment, dbs.connection)
    }

    fun deleteFromIssue(cid: Int) {
        return DeleteComment.execute(cid, dbs.connection)
    }

}