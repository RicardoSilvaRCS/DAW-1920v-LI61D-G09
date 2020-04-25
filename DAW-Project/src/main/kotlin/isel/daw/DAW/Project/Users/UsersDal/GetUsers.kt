package isel.daw.DAW.Project.Users.UsersDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Users.UsersDto.UsersInfoOutputModel
import isel.daw.DAW.Project.Users.UsersDto.UsersNameOutputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class GetUsers {
    companion object {

        private const val GET_ALL_USERS_QUERY = "SELECT username" +
                "FROM users " +
                "where username like ?'%' "


        fun execute ( startName : String , conn : Connection)  : List<UsersNameOutputModel> {
            val ps : PreparedStatement
            val user = mutableListOf<UsersNameOutputModel>()
            try{
                ps = conn.prepareStatement(GET_ALL_USERS_QUERY)
                ps.use {
                    ps.setString(1,startName)
                    val rs = ps.executeQuery()
                    rs.use {
                        while (rs.next()){
                            user.add(UsersNameOutputModel(
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