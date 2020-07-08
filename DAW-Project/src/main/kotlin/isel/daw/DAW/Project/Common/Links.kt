package isel.daw.DAW.Project.Common

/**
 * All paths on the API
 *
 *
 */

/*HOME PATH*/
const val PROJECT_MANAGER_PATH = "/home"


/*USER PAHTS*/
const val USERS_PATH = "/user"
const val GET_ALL_USERS = "$USERS_PATH/userslist/{startName}"
const val GET_USER_INFO = "$USERS_PATH/{userName}"
const val REGISTER_USER_PATH = "$USERS_PATH/register"
const val UPDATE_USER_INFO = USERS_PATH
const val DELETE_USER = "$USERS_PATH/{userName}"
const val LOG_IN_USER_PATH = "$USERS_PATH/login"
const val LOG_OUT_USER_PATH = "$USERS_PATH/logout"
const val ADD_USER_TO_PROJECT = "$USERS_PATH/{userName}/project/{pname}"
const val GET_USER_FRIENDS_LIST = "$USERS_PATH/{userName}/friendslist"
const val ADD_USER_TO_FRIENDS_LIST = "$USERS_PATH/{userName}/friendslist/{friendName}"

/*PROJECT PATHS*/
const val PROJECTS_PATH = "/projects"
const val GET_PROJECTS_PATH = "$PROJECTS_PATH$USERS_PATH/{userName}"
const val GET_SINGLE_PROJECT_PATH = "$PROJECTS_PATH/{pname}"
const val CREATE_PROJECT_PATH ="$PROJECTS_PATH/{userName}"
const val UPDATE_PROJECT_PATH = "$PROJECTS_PATH/{pname}"
const val DELETE_PROJECT_PATH = "$PROJECTS_PATH/{pname}"
const val CREATE_PROJECT_LABEL_PATH = "$PROJECTS_PATH/{pname}/label/{labelName}"
const val DELETE_PROJECT_LABEL_PATH = "$PROJECTS_PATH/{pname}/label/{labelName}"

/*ISSUE PATHS*/
const val ISSUES_PATH = "/issues"
const val GET_ISSUES_PATH = "$ISSUES_PATH/{pname}"
const val GET_SINGLE_ISSUE_PATH = "/issue/{tid}"
const val CREATE_ISSUE_PATH = "$ISSUES_PATH"
const val UPDATE_ISSUE_PATH = "$ISSUES_PATH/{tid}"
const val UPDATE_ISSUE_STATE_PATH = "${UPDATE_ISSUE_PATH}/updatestate"
const val DELETE_ISSUE_PATH = "$ISSUES_PATH/{tid}"
const val CREATE_ISSUE_LABEL_PATH = "$ISSUES_PATH/{tid}/label"
const val DELETE_ISSUE_LABEL_PATH = "$ISSUES_PATH/{tid}/label/{labelName}"

/*COMMENT PATHS*/
const val COMMENTS_PATH = "/comments"
const val GET_COMMENTS_PATH = "$COMMENTS_PATH/{tid}"
const val CREATE_COMMENT_PATH = "$COMMENTS_PATH/{tid}"
const val UPDATE_COMMENT_PATH = "$COMMENTS_PATH/{cid}"
const val DELETE_COMMENT_PATH = "$COMMENTS_PATH/{cid}"