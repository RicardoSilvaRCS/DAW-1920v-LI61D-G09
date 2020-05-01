package isel.daw.DAW.Project.Users

import isel.daw.DAW.Project.Users.UsersDto.*
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Service
@Component
class UsersServices (private val userRepo : UsersRepository) {

    fun getUsers(startName : String) : List<UsersNameOutputModel> {
        return userRepo.getUsers(startName)
    }

    fun getUserInfo(userName : String) : UsersInfoOutputModel {
        return userRepo.getUserInfo(userName)
    }

    fun createUser( newUser : UserInputModel) : UserCreationResponse {
        return userRepo.createUser(newUser)
    }

    fun updateUser( user : UserUpdateInputModel) : UserUpdatedResponse {
        return userRepo.updateUser(user)
    }

    fun deleteUser( userName : String) : UserDeletedResponse {
        return userRepo.deleteUser(userName)
    }

    fun addUserToProject (userName : String , projectName : String) : UserAddedToProjectResponse {
        return userRepo.addUserToProject(userName , projectName)
    }


    /**FRIENDS LIST**/

    fun getUserFriendsList (userName : String )  : List<UsersNameOutputModel> {
        return userRepo.getUserFriendsList(userName)
    }

    fun addUserToFriendsList (userName : String , friendName : String)  : UserAddedToFriendsList{
        return userRepo.addUserToFriendsList(userName, friendName)
    }

    /**AUTH FUNCTIONS**/

    fun validateUserCredentials(authHeader: String): Boolean {
        val decodedHeader =  String(Base64.getDecoder().decode(authHeader.toByteArray())).split(":")
        val user = getUserInfo(decodedHeader[0])
        if(user.fullName.isNullOrEmpty()) return false
        if(decodedHeader[1] == user.password) return true
        return false
    }

}