package isel.daw.DAW.Project.Comments.CommentsDal

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException


class DeleteComment {

companion object{

    private const val DELETE_ISSUE_COMMENT_QUERY : String  = "DELETE FROM issuecomment " +
                                                                " WHERE commentid = ? ;"

    fun execute (commentId : Int ,conn: Connection) {
        val ps : PreparedStatement

        try{
            ps = conn.prepareStatement(DELETE_ISSUE_COMMENT_QUERY)
            ps.use {
                ps.setInt(1,commentId)
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