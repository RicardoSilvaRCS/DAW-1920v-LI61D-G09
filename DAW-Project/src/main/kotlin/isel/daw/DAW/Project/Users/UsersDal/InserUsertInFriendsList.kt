package isel.daw.DAW.Project.Users.UsersDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Users.UsersDto.UserAddedToFriendsList
import java.sql.Connection
import java.sql.SQLException

class InserUsertInFriendsList (){

    companion object{

        private const val INSERT_USER_IN_FRIENDS_LIST_QUERY = "INSERT INTO public.friendslist " +
                "(username, friendname, accepted) " +
                "VALUES (?, ?, ?) "

        fun execute (userName : String , friendName : String , conn : Connection) : UserAddedToFriendsList {
            try {
                conn.autoCommit = false
                val ps = conn.prepareStatement(INSERT_USER_IN_FRIENDS_LIST_QUERY)

                ps.use {
                    ps.setString(1,userName)
                    ps.setString(2,friendName)
                    ps.setInt(3,0)
                    ps.execute()
                }

                conn.commit()
            } catch (ex: SQLException) {
                conn.rollback()
                throw InternalProcedureException("Error during project creation procedure." +
                        "Detailed problem: ${ex.message}")
            }finally {
                conn.close()
            }
            return UserAddedToFriendsList(userName)
        }
    }
}