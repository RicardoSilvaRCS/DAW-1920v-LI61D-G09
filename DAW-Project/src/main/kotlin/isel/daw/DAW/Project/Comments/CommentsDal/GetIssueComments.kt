package isel.daw.DAW.Project.Comments.CommentsDal

import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInfoOutputModel
import isel.daw.DAW.Project.Common.InternalProcedureException
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class GetIssueComments {

    companion object {


        private const val GET_ISSUE_COMMENTS_QUERY = "SELECT commentid, commenttext, commentcreation, issueid " +
                                                    " FROM issuecomment" +
                                                    " where issueid=? "

        fun execute (issueId : Int ,conn: Connection) : List<CommentsInfoOutputModel>{
            val ps : PreparedStatement
            val comments = mutableListOf<CommentsInfoOutputModel>()
            try{
                ps = conn.prepareStatement(GET_ISSUE_COMMENTS_QUERY)
                ps.use {
                    ps.setInt(1,issueId)
                    val rs = ps.executeQuery()
                    rs.use {
                        while (rs.next()){
                            comments.add(CommentsInfoOutputModel(
                                    rs.getInt("commentid"),
                                    rs.getInt("issueid"),
                                    rs.getString("commenttext"),
                                    rs.getTimestamp("commentcreation")))
                        }
                    }
                }
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error obtaining issue's '$issueId' comments." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }

            return comments
        }
    }
}