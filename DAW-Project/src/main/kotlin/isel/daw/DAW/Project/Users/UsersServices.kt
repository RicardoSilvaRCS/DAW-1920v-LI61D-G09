package isel.daw.DAW.Project.Users

import isel.daw.DAW.Project.Common.NoUsersFoundError
import isel.daw.DAW.Project.Common.UserNameConflictException
import isel.daw.DAW.Project.Common.UserNotFoundException
import isel.daw.DAW.Project.Users.UsersDto.*
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Service
@Component
class UsersServices (private val userRepo : UsersRepository) {

    /**
     * If no users are found return specific error
     */
    fun getUsers(startName : String) : List<UsersNameOutputModel> {
        val foundUsers = userRepo.getUsers(startName)
        if(foundUsers.isEmpty()) {
            throw NoUsersFoundError("No users found with $startName name.")
        }
        return foundUsers
    }

    /**
     * If no user is found return specific error
     */
    fun getUserInfo(userName : String) : UsersInfoOutputModel {
        val foundUser = userRepo.getUserInfo(userName)
        if(foundUser.fullName.isNullOrEmpty()) {
            throw UserNotFoundException("No user found with $userName name.")
        }
        return foundUser
    }

    /**
     * Before creating a new one, we verify if a user with the same username
     * already exists, to send the proper error message
     */
    fun createUser( newUser : UserInputModel) : UserCreationResponse {
        val checkUser = userRepo.getUserInfo(newUser.userName)
        if(checkUser.fullName.isNullOrEmpty()) {
            return userRepo.createUser(newUser)
        }
        throw UserNameConflictException("Username ${newUser.userName} already exists")
    }

    fun updateUser( user : UserUpdateInputModel) : UserUpdatedResponse {
        val checkUser = userRepo.getUserInfo(user.userName)
        if(!checkUser.fullName.isNullOrEmpty()) {
            return userRepo.updateUser(user)
        }
        throw UserNotFoundException("No user found with ${user.userName} name.")
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