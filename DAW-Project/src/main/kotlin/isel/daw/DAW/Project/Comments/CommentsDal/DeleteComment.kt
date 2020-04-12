package isel.daw.DAW.Project.Comments.CommentsDal

import isel.daw.DAW.Project.Common.InternalProcedureException
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
            throw InternalProcedureException("Error during deletion procedure of comment with id '$commentId'." +
                    "Detailed problem: ${ex.message}")
        } finally {
            conn.close()
        }
    }

}

}