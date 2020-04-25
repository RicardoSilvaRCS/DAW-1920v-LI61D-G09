package isel.daw.DAW.Project.Users.UsersDal


import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Users.UsersDto.UserUpdateInputModel
import isel.daw.DAW.Project.Users.UsersDto.UserUpdatedResponse
import java.sql.Connection
import java.sql.SQLException

class UpdateUserInfo {
    companion object {

        private const val UPDATE_USER_QUERY : String  = "UPDATE public.users " +
                "SET email=?, phonenumber=?, pass=? " +
                "WHERE userName = ? "

        fun execute (user : UserUpdateInputModel, conn : Connection) : UserUpdatedResponse {
            try {

                val ps = conn.prepareStatement(UPDATE_USER_QUERY)

                ps.use {
                    ps.setString(1,user.email)
                    ps.setString(2,user.phoneNumber)
                    ps.setString(3,user.password)
                    ps.setString(4,user.userName)
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
            return UserUpdatedResponse(user.userName)
        }
    }
}