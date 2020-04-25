package isel.daw.DAW.Project.Users.UsersDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Users.UsersDto.UserDeletedResponse
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class DeleteUser {

    companion object {
        private const val DELETE_PROJECT_QUERY = "delete from users where username = ? ;"

        fun execute(userName: String, conn: Connection) : UserDeletedResponse {
            val ps : PreparedStatement
            try{
                ps = conn.prepareStatement(DELETE_PROJECT_QUERY)
                ps.use {
                    ps.setString(1,userName)
                    ps.execute()
                }
            }catch ( ex : SQLException){
                conn.rollback()
                throw InternalProcedureException("Error during project $userName deletion." +
                        "Detailed problem: ${ex.message}")
            } finally {
                conn.close()
            }
            return UserDeletedResponse ()
        }
    }
}