package isel.daw.DAW.Project.Users

import isel.daw.DAW.Project.Users.UsersDto.UserCreationResponse
import isel.daw.DAW.Project.Users.UsersDto.UserInputModel
import isel.daw.DAW.Project.Users.UsersDto.UsersInfoOutputModel

class UsersServices (private val userRepo : UsersRepository) {

    fun getUsers(startName : String) : List<UsersInfoOutputModel> {
        return userRepo.getUsers(startName)
    }

    fun getUserInfo(userName : String) : UsersInfoOutputModel {
        return userRepo.getUserInfo(userName)
    }

    fun createUser( newUser : UserInputModel) : UserCreationResponse {
        return userRepo.createUser(newUser)
    }

    fun updateUser( user : UserInputModel) {
        return userRepo.updateUser(user)
    }

    fun deleteUser( userName : String) {
        return userRepo.deleteUser(userName)
    }

    fun addUserToProject (userName : String , projectName : String) {
        return userRepo.addUserToProject(userName , projectName)
    }

    fun addUserToFriendsList (userName : String , friendName : String) {
        return userRepo.addUserToFriendsList(userName, friendName)
    }

}