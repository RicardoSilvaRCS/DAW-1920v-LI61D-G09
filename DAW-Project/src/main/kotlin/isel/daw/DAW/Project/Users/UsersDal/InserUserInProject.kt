package isel.daw.DAW.Project.Users.UsersDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Users.UsersDto.UserAddedToProjectResponse
import java.sql.Connection
import java.sql.SQLException

class InserUserInProject () {


    companion object{

        private val INSERT_USER_INTO_PROJECT_QUERY = " INSERT INTO public.usersprojects " +
                "(username, projname) " +
                "VALUES (?, ?) "

        fun execute (userName : String , projectName : String , conn : Connection) : UserAddedToProjectResponse {
            try {

                val ps = conn.prepareStatement(INSERT_USER_INTO_PROJECT_QUERY)

                ps.use {
                    ps.setString(1,userName)
                    ps.setString(2,projectName)
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
            return UserAddedToProjectResponse(userName , projectName)
        }
    }
}
