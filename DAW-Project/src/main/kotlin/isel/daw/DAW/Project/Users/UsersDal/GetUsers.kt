package isel.daw.DAW.Project.Users.UsersDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Users.UsersDto.UsersInfoOutputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class GetUsers {
    companion object {

        private  val GET_ALL_USERS_QUERY = "SELECT username" +
                "FROM users " +
                "where username like '?%' "


        fun execute ( startName : String , conn : Connection)  : List<UsersInfoOutputModel> {
            val ps : PreparedStatement
            val user = mutableListOf<UsersInfoOutputModel>()
            try{
                ps = conn.prepareStatement(GET_ALL_USERS_QUERY)
                ps.use {
                    ps.setString(1,startName)
                    val rs = ps.executeQuery()
                    rs.use {
                        while (rs.next()){
                            user.add(UsersInfoOutputModel(
                                    rs.getString("username")
                                )
                            )
                        }
                    }
                }
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error obtaining issue's '$startName' comments." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }

            return user
        }
    }
}