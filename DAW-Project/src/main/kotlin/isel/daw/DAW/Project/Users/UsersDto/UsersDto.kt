package isel.daw.DAW.Project.Users.UsersDto

import isel.daw.DAW.Project.Common.*
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.net.URI
import java.sql.Timestamp

/**---------------------------------------------------INPUT DTOS--------------------------------------------*/

/**
 *  Data model for the creation of a new User
 */
class UserInputModel (
        var userName: String,
        var fullName : String,
        var email : String,
        var age : Int,
        var genre : String,
        var phoneNumber : String,
        var password : String
)

class UserUpdateInputModel (
        var userName: String,
        var email : String,
        var phoneNumber : String,
        var password : String
)

class FriendshipInputModel (
        var requestedUser: String,
        var currentUser : String,
        var accepted : Boolean
)

class LoginInputModel(
        var userName: String,
        var password: String
)

/**---------------------------------------------------OUTPUT DTOS--------------------------------------------*/

/**
 * Response for sucessfull user login
 */
class UserSucessLoginResponse {

    inner class PropertiesJson(
            val timestamp: Timestamp,
            val date: String,
            val status: Int,
            val message: String
    )

    var properties: UserSucessLoginResponse.PropertiesJson? = null

    init {
        val timestamp = Timestamp(System.currentTimeMillis())
        properties = PropertiesJson(
                timestamp = timestamp,
                date =  timestamp.toString(),
                status = HttpStatus.ACCEPTED.value(),
                message = "User logged in with sucess"
        )
    }
}


/**
 *  Data model for the representation of a User
 */

class UsersInfoOutputModel (var userName: String,
                            var  fullName: String? ="",
                            var email: String? ="",
                            var age: Int? = 0,
                            var genre: String? = "",
                            var phoneNumber: String? = "",
                            var  password: String? = ""
) {

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("UserInfo"),
            links = listOf(
                    SirenLink(rel = listOf("update-user-info"), href = URI(UPDATE_USER_INFO.replace("{userName}",this.userName))),
                    SirenLink(rel = listOf("delete-user"), href = URI(DELETE_USER.replace("{userName}",this.userName)))
            ),
            actions = listOf(UPDATE_USER_ACTION, DELETE_USER_ACTION, ADD_USER_TO_FRIENDSLIST_ACTION, ADD_USER_TO_PROJECT_ACTION)
    )
}


class UsersNameOutputModel (var userName: String) {

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("UserInfo"),
            links = listOf(),
            actions = listOf(ADD_USER_TO_FRIENDSLIST_ACTION,ADD_USER_TO_PROJECT_ACTION)
    )

}




class UserCreationResponse(
        val name: String
){
    val message: String = "User created with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.CREATED

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("ProjectCreation"),
            links = listOf(

            ),
            actions = listOf(GET_USER_ACTION, UPDATE_USER_ACTION, DELETE_USER_ACTION, ADD_USER_TO_FRIENDSLIST_ACTION, ADD_USER_TO_PROJECT_ACTION)
    )
}

class UserUpdatedResponse(
        val userName: String
){
    val message: String = "User updated with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("ProjectUpdated"),
            links = listOf(
                    SirenLink(rel = listOf("update-user-info"), href = URI(UPDATE_USER_INFO.replace("{userName}",this.userName))),
                    SirenLink(rel = listOf("delete-user"), href = URI(DELETE_USER.replace("{userName}",this.userName)))
            ),
            actions = listOf(UPDATE_USER_ACTION, DELETE_USER_ACTION, ADD_USER_TO_FRIENDSLIST_ACTION, ADD_USER_TO_PROJECT_ACTION)
    )
}


class UserDeletedResponse()
{
    val message: String = "User deleted with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("UserDeleted"),
            links = listOf(),
            actions = listOf(CREATE_USER_ACTION)
    )
}

class UserAddedToProjectResponse(
        val userName: String,
        val projectName : String
){
    val message: String = "User added to project with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("ProjectUpdated"),
            links = listOf(
                    SirenLink(rel = listOf("update-user-info"), href = URI(UPDATE_USER_INFO.replace("{userName}",this.userName))),
                    SirenLink(rel = listOf("delete-user"), href = URI(DELETE_USER.replace("{userName}",this.userName))),
                    SirenLink(rel = listOf("get-user-project"), href = URI(GET_PROJECTS_PATH.replace("{userName}",this.userName)))
            ),
            actions = listOf(UPDATE_USER_ACTION, DELETE_USER_ACTION, ADD_USER_TO_FRIENDSLIST_ACTION, ADD_USER_TO_PROJECT_ACTION)
    )
}


/**FRIENDS LIST OUTPUT MODEL**/

class UserFriendsListOutputModel (var userName: String

) {

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("UserInfo"),
            links = listOf(),
            actions = listOf(ADD_USER_TO_FRIENDSLIST_ACTION,ADD_USER_TO_PROJECT_ACTION)
    )
}

class UserAddedToFriendsList(
        val userName: String
){
    val message: String = "$userName added to friends list with success."
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val date: String = timestamp.toString()
    val status: HttpStatus = HttpStatus.OK

    fun toSirenObject() = SirenEntity(
            properties = this,
            clazz = listOf("ProjectUpdated"),
            links = listOf(
                    SirenLink(rel = listOf("update-user-info"), href = URI(UPDATE_USER_INFO.replace("{userName}",this.userName))),
                    SirenLink(rel = listOf("delete-user"), href = URI(DELETE_USER.replace("{userName}",this.userName))),
                    SirenLink(rel = listOf("get-user-project"), href = URI(GET_PROJECTS_PATH.replace("{userName}",this.userName)))
            ),
            actions = listOf(UPDATE_USER_ACTION, DELETE_USER_ACTION, ADD_USER_TO_FRIENDSLIST_ACTION, ADD_USER_TO_PROJECT_ACTION)
    )
}



/**---------------------------SIREN ACTIONS------------------------------------------*/

/**This describe the possible actions*/

val GET_USER_ACTION = SirenAction(
        name = "get-project",
        title = "Get the user with the username",
        href = URI(GET_USER_INFO.replace("{",":").replace("}","")),
        method = HttpMethod.GET
)

val CREATE_USER_ACTION = SirenAction(
        name = "create-new-user",
        title = "Create a new project",
        href = URI(REGISTER_USER_PATH.replace("{",":").replace("}","")),
        method = HttpMethod.POST,
        type = MediaType.APPLICATION_JSON
)

val UPDATE_USER_ACTION = SirenAction(
        name = "update-user",
        title = "Update the user info",
        href = URI(UPDATE_USER_INFO.replace("{",":").replace("}","")),
        method = HttpMethod.PUT,
        type = MediaType.APPLICATION_JSON,
        fields = listOf(SirenAction.Field("userName" , "string"))
)

val DELETE_USER_ACTION = SirenAction(
        name = "delete-user",
        title = "Delete specified user",
        href = URI(DELETE_USER.replace("{",":").replace("}","")),
        method = HttpMethod.DELETE,
        fields = listOf(SirenAction.Field("userName" , "string"))
)

val ADD_USER_TO_FRIENDSLIST_ACTION = SirenAction(
        name = "add-friend-to-list",
        title = "Add a friend to user's friends list",
        href = URI(ADD_USER_TO_FRIENDS_LIST.replace("{",":").replace("}","")),
        method = HttpMethod.PUT,
        fields = listOf(SirenAction.Field("userName" , "string"),SirenAction.Field("friendName" , "string"))
)

val ADD_USER_TO_PROJECT_ACTION = SirenAction(
        name = "add-user-to-project",
        title = "Add user to project",
        href = URI(ADD_USER_TO_PROJECT.replace("{",":").replace("}","")),
        method = HttpMethod.PUT,
        fields = listOf(SirenAction.Field("userName" , "string"),SirenAction.Field("pname" , "string"))
)

