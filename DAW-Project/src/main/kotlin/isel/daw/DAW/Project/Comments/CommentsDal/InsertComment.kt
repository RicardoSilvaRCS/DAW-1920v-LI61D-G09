package isel.daw.DAW.Project.Comments.CommentsDal

import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInputModel
import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.SQLException
import java.time.LocalDate

class InsertComment {

    companion object {

        const val INSERT_COMMENT_INTO_ISSUE_QUERY = "INSERT INTO public.issuecomment" +
                " (commenttext, commentcreation, issueid) " +
                " VALUES (?, ?, ?);" ;

        fun execute (issueid : Int , comment : CommentsInputModel , conn : Connection) {
            val ps : PreparedStatement

            try{
                ps = conn.prepareStatement(INSERT_COMMENT_INTO_ISSUE_QUERY)
                ps.use {
                    ps.setString(1,comment.text)
                    ps.setDate(2, Date.valueOf(LocalDate.now()))
                    ps.setInt(3,issueid)
                    ps.executeQuery()
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