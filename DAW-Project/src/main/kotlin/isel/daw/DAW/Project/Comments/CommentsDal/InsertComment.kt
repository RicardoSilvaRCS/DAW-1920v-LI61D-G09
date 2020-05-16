package isel.daw.DAW.Project.Comments.CommentsDal

import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsCreatedResponse
import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInputModel
import isel.daw.DAW.Project.Common.InternalProcedureException
import java.sql.*
import java.time.LocalDate

class InsertComment {

    companion object {

        const val INSERT_COMMENT_INTO_ISSUE_QUERY = "INSERT INTO issuecomment" +
                " (commenttext, commentcreation, issueid)" +
                " VALUES (?, ?, ?)" +
                " RETURNING issuecomment.commentid" ;

        fun execute (issueid : Int , comment : CommentsInputModel , conn : Connection): CommentsCreatedResponse {
            val ps : PreparedStatement
            var cid: Int = -1

            try{
                conn.autoCommit = false
                ps = conn.prepareStatement(INSERT_COMMENT_INTO_ISSUE_QUERY)
                ps.use {
                    ps.setString(1,comment.text)
                    ps.setTimestamp(2, Timestamp(System.currentTimeMillis()))
                    ps.setInt(3,issueid)
                    val rs = ps.executeQuery()
                    if(rs.next()) {
                        cid = rs.getInt("commentid")
                    }
                }
                conn.commit()
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error during comment insertion procedure." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return CommentsCreatedResponse(issueid, cid)
        }
    }
}