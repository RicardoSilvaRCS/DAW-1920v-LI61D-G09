package isel.daw.DAW.Project.Comments.CommentsDal

import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInputModel
import isel.daw.DAW.Project.Common.InternalProcedureException
import java.sql.*
import java.time.LocalDate

class InsertComment {

    companion object {

        const val INSERT_COMMENT_INTO_ISSUE_QUERY = "INSERT INTO public.issuecomment" +
                " (commenttext, commentcreation, issueid) " +
                " VALUES (?, ?, ?);" ;

        fun execute (issueid : Int , comment : CommentsInputModel , conn : Connection) {
            val ps : PreparedStatement

            try{
                conn.autoCommit = false
                ps = conn.prepareStatement(INSERT_COMMENT_INTO_ISSUE_QUERY)
                ps.use {
                    ps.setString(1,comment.text)
                    ps.setTimestamp(2, Timestamp(System.currentTimeMillis()))
                    ps.setInt(3,issueid)
                    ps.execute()
                }
                conn.commit()
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error during comment insertion procedure." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
        }
    }
}