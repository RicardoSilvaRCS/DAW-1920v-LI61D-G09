package isel.daw.DAW.Project.Comments.CommentsDal

import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class UpdateComment {

    companion object {
        const val UPDATE_ISSUE_COMMENT_QUERY = "UPDATE issuecomment " +
                                                    " SET commenttext = ? " +
                                                    " WHERE commentid = ? "

        fun execute ( commentId : Int , comment :CommentsInputModel , conn : Connection) {
            val ps : PreparedStatement

            try{
                ps = conn.prepareStatement(UPDATE_ISSUE_COMMENT_QUERY)
                ps.use {
                    ps.setString(1,comment.text)
                    ps.setInt(2,commentId)
                    ps.execute()
                }
            }catch ( ex : SQLException){
                conn.rollback()
                print(ex)
            } finally {
                conn.close()
            }
        }
    }
}