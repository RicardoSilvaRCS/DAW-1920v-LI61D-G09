package isel.daw.DAW.Project.Users

import isel.daw.DAW.Project.Common.LOG_IN_USER_PATH
import isel.daw.DAW.Project.Common.LOG_OUT_USER_PATH
import isel.daw.DAW.Project.Common.REGISTER_USER_PATH
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

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
class UsersController {

    @PostMapping(REGISTER_USER_PATH)
    fun registerUser() {
        throw NotImplementedError("TODO!")
    }

    @PostMapping(LOG_IN_USER_PATH)
    fun loginUser() {
        throw NotImplementedError("TODO!")
    }

    @PutMapping(LOG_OUT_USER_PATH)
    fun logoutUser() {
        throw NotImplementedError("TODO!")
    }

}