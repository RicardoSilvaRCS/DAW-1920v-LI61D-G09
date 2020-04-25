package isel.daw.DAW.Project.Users

import isel.daw.DAW.Project.Common.*
import isel.daw.DAW.Project.Users.UsersDto.*
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

    @GetMapping(GET_ALL_USERS)
    fun getUsers(@PathVariable startName : String) : List<SirenEntity<UsersNameOutputModel>>{
        val users: MutableList<SirenEntity<UsersNameOutputModel>> = mutableListOf()
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
    fun updateUser(@RequestBody user : UserUpdateInputModel) : SirenEntity<UserUpdatedResponse>{
        return userServices.updateUser(user).toSirenObject()
    }

    @DeleteMapping(DELETE_USER)
    fun deleteUser(@PathVariable userName : String) : SirenEntity<UserDeletedResponse>{
        return userServices.deleteUser(userName).toSirenObject()
    }

    /**USER LOGIN AND LOGOUT**/

    @PutMapping(LOG_IN_USER_PATH)
    fun loginUser() {
        throw NotImplementedError("TODO!")
    }

    @PutMapping(LOG_OUT_USER_PATH)
    fun logoutUser() {
        throw NotImplementedError("TODO!")
    }

    /**USER ASSOCIATION WITH PROJECT**/

    @PutMapping(ADD_USER_TO_PROJECT)
    fun addUserToProject(@PathVariable userName : String , @PathVariable pname : String) : SirenEntity<UserAddedToProjectResponse> {
        return userServices.addUserToProject(userName , pname).toSirenObject()
    }

    /**FRIENDS SECTION**/

    @GetMapping(GET_USER_FRIENDS_LIST)
    fun getFriends(@PathVariable userName : String) : List<SirenEntity<UsersNameOutputModel>>{
        val friends: MutableList<SirenEntity<UsersNameOutputModel>> = mutableListOf()
        userServices.getUserFriendsList(userName).forEach{
            friends.add(it.toSirenObject())
        }
        return friends
    }

    @PutMapping(ADD_USER_TO_FRIENDS_LIST)
    fun addUserToFriendsList(@PathVariable userName : String , @PathVariable friendName : String) : SirenEntity<UserAddedToFriendsList>{
        return userServices.addUserToFriendsList(userName , friendName).toSirenObject()
    }

}