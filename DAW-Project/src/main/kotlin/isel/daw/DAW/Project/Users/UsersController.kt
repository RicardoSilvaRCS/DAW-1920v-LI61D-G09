package isel.daw.DAW.Project.Users

import isel.daw.DAW.Project.Common.*
import isel.daw.DAW.Project.Users.UsersDto.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Timestamp
import java.util.*
import javax.servlet.http.HttpServletRequest

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
class UsersController (val userServices : UsersServices) {

    @GetMapping(GET_ALL_USERS)
    fun getUsers(@PathVariable startName : String) : List<SirenEntity<UsersNameOutputModel>>{
        val users: MutableList<SirenEntity<UsersNameOutputModel>> = mutableListOf()
        userServices.getUsers(startName).forEach{
            users.add(it.toSirenObject())
        }
        return users
    }

    @AuthRequired
    @GetMapping(GET_USER_INFO)
    fun getUserInfo(@PathVariable userName : String) : SirenEntity<UsersInfoOutputModel>{
        return userServices.getUserInfo(userName).toSirenObject()
    }

    @PostMapping(REGISTER_USER_PATH)
    fun registerUser(@RequestBody newUser : UserInputModel) : SirenEntity<UserCreationResponse> {
        return userServices.createUser(newUser).toSirenObject()
    }

    @AuthRequired
    @PutMapping(UPDATE_USER_INFO)
    fun updateUser(@RequestBody user : UserUpdateInputModel) : SirenEntity<UserUpdatedResponse>{
        return userServices.updateUser(user).toSirenObject()
    }

    @AuthRequired
    @DeleteMapping(DELETE_USER)
    fun deleteUser(@PathVariable userName : String) : SirenEntity<UserDeletedResponse>{
        return userServices.deleteUser(userName).toSirenObject()
    }

    /**USER LOGIN AND LOGOUT**/

    @PostMapping(LOG_IN_USER_PATH)
    fun loginUser(@RequestBody userToLogin: LoginInputModel): ResponseEntity<UserSucessLoginResponse> {
        val user: UsersInfoOutputModel = userServices.getUserInfo(userToLogin.userName)
        if(user.fullName.isNullOrEmpty()) {
            throw UserNotFoundException("User ${userToLogin.userName} doesn't exist")
        }
        if(userToLogin.password != user.password){
            throw InvalidCredentialsException("Credentials given are invalid.")
        }

        val authString = "${user.userName}:${user.password}"

        return ResponseEntity
                .accepted()
                .header(AUTH_HEADER, "BASIC", Base64.getEncoder().encodeToString(authString.toByteArray()))
                //.header(AUTH_HEADER, "BASIC ${Base64.getEncoder().encodeToString(authString.toByteArray())}")
                .header("Access-Control-Expose-Headers", "*", "Authorization")
                .body(UserSucessLoginResponse())

    }

    @AuthRequired
    @PutMapping(LOG_OUT_USER_PATH)
    fun logoutUser() {
        throw NotImplementedError("TODO!")
    }

    /**USER ASSOCIATION WITH PROJECT**/
    @AuthRequired
    @PutMapping(ADD_USER_TO_PROJECT)
    fun addUserToProject(@PathVariable userName : String , @PathVariable pname : String) : SirenEntity<UserAddedToProjectResponse> {
        return userServices.addUserToProject(userName , pname).toSirenObject()
    }

    /**FRIENDS SECTION**/
    @AuthRequired
    @GetMapping(GET_USER_FRIENDS_LIST)
    fun getFriends(@PathVariable userName : String) : List<SirenEntity<UsersNameOutputModel>>{
        val friends: MutableList<SirenEntity<UsersNameOutputModel>> = mutableListOf()
        userServices.getUserFriendsList(userName).forEach{
            friends.add(it.toSirenObject())
        }
        return friends
    }
    @AuthRequired
    @PutMapping(ADD_USER_TO_FRIENDS_LIST)
    fun addUserToFriendsList(@PathVariable userName : String , @PathVariable friendName : String) : SirenEntity<UserAddedToFriendsList>{
        return userServices.addUserToFriendsList(userName , friendName).toSirenObject()
    }

}