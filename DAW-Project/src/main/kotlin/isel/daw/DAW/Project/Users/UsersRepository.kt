package isel.daw.DAW.Project.Users

import isel.daw.DAW.Project.Users.UsersDal.*
import isel.daw.DAW.Project.Users.UsersDto.*
import org.springframework.beans.factory.annotation.Autowired
import javax.sql.DataSource

class UsersRepository (@Autowired val dbs: DataSource) {

    fun getUsers (startName : String) : List<UsersNameOutputModel> {
        return GetUsers.execute(startName , dbs.connection)
    }

    fun getUserInfo (username : String) : UsersInfoOutputModel {
        return GetUser.execute(username , dbs.connection)
    }

    fun createUser (newUser : UserInputModel) : UserCreationResponse {
        return CreateUser.execute(newUser , dbs.connection)
    }

    fun updateUser (user : UserUpdateInputModel)  : UserUpdatedResponse{
        return UpdateUserInfo.execute(user , dbs.connection)
    }

    fun deleteUser (userName : String) : UserDeletedResponse {
        return DeleteUser.execute(userName , dbs.connection)
    }

    fun addUserToProject (newUser : String , projname : String ) : UserAddedToProjectResponse{
        return InserUserInProject.execute(newUser , projname, dbs.connection)
    }

    fun addUserToFriendsList (newUser : String, friendName : String) : UserAddedToFriendsList{
        return InserUsertInFriendsList.execute(newUser,friendName, dbs.connection)
    }
}