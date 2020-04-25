package isel.daw.DAW.Project.Users

import isel.daw.DAW.Project.Users.UsersDal.*
import isel.daw.DAW.Project.Users.UsersDto.UserCreationResponse
import isel.daw.DAW.Project.Users.UsersDto.UserInputModel
import isel.daw.DAW.Project.Users.UsersDto.UsersInfoOutputModel
import org.springframework.beans.factory.annotation.Autowired
import java.sql.Connection
import javax.sql.DataSource

class UsersRepository (@Autowired val dbs: DataSource) {

    fun getUsers (startName : String) : List<UsersInfoOutputModel> {
        return GetUsers.execute(startName , dbs.connection)
    }

    fun getUserInfo (username : String) : UsersInfoOutputModel {
        return GetUser.execute(username , dbs.connection)
    }

    fun createUser (newUser : UserInputModel) : UserCreationResponse {
        return CreateUser.execute(newUser , dbs.connection)
    }

    fun updateUser (newUser : UserInputModel) {
        return UpdateUserInfo.execute(newUser , dbs.connection)
    }

    fun deleteUser (userName : String) {
        return DeleteUser.execute(userName , dbs.connection)
    }

    fun addUserToProject (newUser : String , projname : String ){
        return InserUserInProject.execute(newUser , projname, dbs.connection)
    }

    fun addUserToFriendsList (newUser : String, friendName : String){
        return InserUsertInFriendsList.execute(newUser,friendName, dbs.connection)
    }
}