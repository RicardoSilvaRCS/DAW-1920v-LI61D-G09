package isel.daw.DAW.Project.Users.UsersDal

import isel.daw.DAW.Project.Common.InternalProcedureException
import isel.daw.DAW.Project.Users.UsersDto.UserCreationResponse
import isel.daw.DAW.Project.Users.UsersDto.UserInputModel
import java.sql.Connection
import java.sql.SQLException

class CreateUser {

    companion object {

        private const val CREATE_USER_QUERY : String  = "INSERT INTO users " +
                "(username, fullname, email, age, genre, phonenumber, pass) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);"

        fun execute (newUser : UserInputModel , conn : Connection) : UserCreationResponse {
            try {
                conn.autoCommit = false
                val ps = conn.prepareStatement(CREATE_USER_QUERY)

                ps.use {
                    ps.setString(1, newUser.userName)
                    ps.setString(2,newUser.fullName)
                    ps.setString(3,newUser.email)
                    ps.setInt(4,newUser.age)
                    ps.setString(5,newUser.genre)
                    ps.setString(6,newUser.phoneNumber)
                    ps.setString(7,newUser.password)
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
            return UserCreationResponse(newUser.userName)
        }
    }
}
