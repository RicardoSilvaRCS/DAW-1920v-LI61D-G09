package isel.daw.DAW.Project.Users.UsersDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Users.UsersDto.UsersInfoOutputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class GetUser {

    companion object {

        private const val GET_USER_INFO_QUERY = "SELECT username, fullname, email, age, genre, phonenumber, pass " +
                "FROM users " +
                "where username = ?"


        fun execute (userName : String , conn : Connection)  : UsersInfoOutputModel{
            val ps : PreparedStatement
            val user = UsersInfoOutputModel(userName)
            try{
                ps = conn.prepareStatement(GET_USER_INFO_QUERY)
                ps.use {
                    ps.setString(1,userName)
                    val rs = ps.executeQuery()
                    rs.use {
                        if (rs.next()){
                            user.fullName = rs.getString("fullname")
                            user.email = rs.getString("email")
                            user.age = rs.getInt("age")
                            user.genre = rs.getString("genre")
                            user.phoneNumber = rs.getString("phonenumber")
                            user.password = rs.getString("pass")
                        }
                    }
                }
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error obtaining issue's '$userName' comments." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }

            return user
        }
    }
}