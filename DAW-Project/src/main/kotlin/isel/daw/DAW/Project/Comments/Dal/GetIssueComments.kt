package isel.daw.DAW.Project.Comments.Dal

import isel.daw.DAW.Project.Comments.CommentsDtos.CommentsInfoOutputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class GetIssueComments {

    companion object {


        private const val GET_ISSUE_COMMENTS_QUERY = "SELECT commentid, commenttext, commentcreation, issueid " +
                                                    "FROM public.issuecomment" +
                                                    "where issueid = ? "

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
                                    rs.getDate("commentcreation")))
                        }
                    }
                }
            }catch ( ex : SQLException){
                print(ex)
            } finally {
                conn.close()
            }

            return comments
        }
    }
}