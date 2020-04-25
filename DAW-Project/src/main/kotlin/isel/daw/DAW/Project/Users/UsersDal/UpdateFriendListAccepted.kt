package isel.daw.DAW.Project.Users.UsersDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Users.UsersDto.FriendshipInputModel
import isel.daw.DAW.Project.Users.UsersDto.UserAddedToFriendsList
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class UpdateFriendListAccepted {

    companion object{

        private const val UPDATE_ACCEPTED_QUERY = "UPDATE friendslist SET accepted=? " +
                "WHERE " +
                "username = ? " +
                "and friendName = ?  "

        private const val DELETE_FIENDSHIP_QUERY = "DELETE FROM public.friendslist " +
                "WHERE username = ? and friendname = ?;"

        fun execute (friendship : FriendshipInputModel, conn : Connection) : UserAddedToFriendsList{
            try {

                if(friendship.accepted) {
                   val  ps = conn.prepareStatement(UPDATE_ACCEPTED_QUERY)
                    ps.use {
                        ps.setInt(1,1)
                        ps.setString(2,friendship.requestedUser)
                        ps.setString(3,friendship.currentUser)
                        ps.execute()
                    }
                }
                else{
                    val ps = conn.prepareStatement(DELETE_FIENDSHIP_QUERY)
                    ps.use {
                        ps.setString(1,friendship.requestedUser)
                        ps.setString(2,friendship.currentUser)
                        ps.execute()
                    }
                }
                conn.commit()
            } catch (ex: SQLException) {
                conn.rollback()
                throw InternalProcedureException("Error during project creation procedure." +
                        "Detailed problem: ${ex.message}")
            }finally {
                conn.close()
            }
            return UserAddedToFriendsList(friendship.currentUser)
        }
    }
}