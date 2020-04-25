package isel.daw.DAW.Project.Users.UsersDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Users.UsersDto.UsersNameOutputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class GetUserFriendsList {

    companion object {

        private const val GET_ALL_FRIENDS_QUERY = "SELECT friendname " +
                "FROM friendslist " +
                "where username = ? " +
                "and accepted = 1"

        fun execute(userName: String, conn: Connection): List<UsersNameOutputModel> {
            val ps: PreparedStatement
            val user = mutableListOf<UsersNameOutputModel>()
            try {
                ps = conn.prepareStatement(GET_ALL_FRIENDS_QUERY)
                ps.use {
                    ps.setString(1, userName)
                    val rs = ps.executeQuery()
                    rs.use {
                        while (rs.next()) {
                            user.add(UsersNameOutputModel(
                                    rs.getString("friendname")
                            )
                            )
                        }
                    }
                }
            } catch (ex: SQLException) {
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