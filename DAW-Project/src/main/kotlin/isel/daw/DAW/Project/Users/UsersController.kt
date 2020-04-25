package isel.daw.DAW.Project.Users

import isel.daw.DAW.Project.Common.*
import isel.daw.DAW.Project.Users.UsersDto.UserCreationResponse
import isel.daw.DAW.Project.Users.UsersDto.UserInputModel
import isel.daw.DAW.Project.Users.UsersDto.UsersInfoOutputModel
import org.springframework.web.bind.annotation.*

/**
 * Controller for User resources
 */

/**
 * TODO: For now the only necessary functions will be Register, Login and Logout.
 *       The authentication will be done with a sort of "middleware" not sure how.
 *       In the future we will add the endpoints for updateUser, projects of User, etc.
 *       No service, repo, dal, dtos nor exceptions were made yet, just Links and Controller
 *
 */
@RestController
class UsersController (private val userServices : UsersServices) {

    @GetMapping(GET_USERS)
    fun getUsers(@PathVariable startName : String) : List<SirenEntity<UsersInfoOutputModel>>{
        val users: MutableList<SirenEntity<UsersInfoOutputModel>> = mutableListOf()
        userServices.getUsers(startName).forEach{
            users.add(it.toSirenObject())
        }
        return users
    }

    @GetMapping(GET_USER_INFO)
    fun getUserInfo(@PathVariable userName : String) : SirenEntity<UsersInfoOutputModel>{
        return userServices.getUserInfo(userName).toSirenObject()
    }

    @PostMapping(REGISTER_USER_PATH)
    fun registerUser(@RequestBody newUser : UserInputModel) : SirenEntity<UserCreationResponse> {
        return userServices.createUser(newUser).toSirenObject()
    }

    @PutMapping(UPDATE_USER_INFO)
    fun updateUser(@RequestBody user : UserInputModel) {
        return userServices.updateUser(user)
    }

    @DeleteMapping(DELETE_USER)
    fun updateUser(userName : String) {
        return userServices.deleteUser(userName)
    }

    @PutMapping(LOG_IN_USER_PATH)
    fun loginUser() {
        throw NotImplementedError("TODO!")
    }

    @PutMapping(LOG_OUT_USER_PATH)
    fun logoutUser() {
        throw NotImplementedError("TODO!")
    }

    @PutMapping(ADD_USER_TO_PROJECT)
    fun addUserToProject(@PathVariable userName : String , @PathVariable projectName : String){
        return userServices.addUserToProject(userName , projectName)
    }

    @PutMapping(ADD_USER_TO_FRIENDS_LIST)
    fun addUserToFriendsList(@PathVariable userName : String , @PathVariable friendName : String){
        return userServices.addUserToFriendsList(userName , friendName)
    }

    //TODO fazer dois objetos de retorno que irá determinar o que o user poderá fazer com estes ultimos endpoints (25/04/2020 faço so para não esquecer)
}