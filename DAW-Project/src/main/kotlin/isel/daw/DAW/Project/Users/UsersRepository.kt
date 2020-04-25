package isel.daw.DAW.Project.Users

import isel.daw.DAW.Project.Users.UsersDal.*
import isel.daw.DAW.Project.Users.UsersDto.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
@Component
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

    fun addUserToProject (user : String , projname : String ) : UserAddedToProjectResponse{
        return InserUserInProject.execute(user , projname, dbs.connection)
    }

    fun getUserFriendsList (userName : String) : List<UsersNameOutputModel>{
        return GetUserFriendsList.execute(userName, dbs.connection)
    }

    fun addUserToFriendsList (user : String, friendName : String) : UserAddedToFriendsList{
        return InserUsertInFriendsList.execute(user,friendName, dbs.connection)
    }
}