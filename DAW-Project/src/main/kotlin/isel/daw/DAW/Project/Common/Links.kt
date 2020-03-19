package isel.daw.DAW.Project.Common

/**
 * All paths on the API
 */

/*PROJECT PATHS*/
const val PROJECTS_PATH = "/projects"
const val GET_PROJECTS_PATH = "${PROJECTS_PATH}/getprojects"
const val GET_SINGLE_PROJECT_PATH = "${PROJECTS_PATH}/getproject"
const val CREATE_PROJECT_PATH = "${PROJECTS_PATH}/createproject"
const val UPDATE_PROJECT_PATH = "${PROJECTS_PATH}/updateproject"
const val DELETE_PROJECT_PATH = "${PROJECTS_PATH}/deleteproject"

/*ISSUE PATHS*/
const val ISSUES_PATH = "/issues"
const val GET_ISSUES_PATH = "${ISSUES_PATH}/getissues"
const val GET_SINGLE_ISSUE_PATH = "${ISSUES_PATH}/getissue"
const val CREATE_ISSUE_PATH = "${ISSUES_PATH}/createissue"
const val UPDATE_ISSUE_PATH = "${ISSUES_PATH}/updateissue/{tid}"
const val UPDATE_ISSUE_STATE_PATH = "${UPDATE_ISSUE_PATH}/updatestate"
const val DELETE_ISSUE_PATH = "${ISSUES_PATH}/deleteissue/{tid}"

/*COMMENT PATHS*/
const val COMMENTS_PATH = "/comments"
const val GET_COMMENTS_PATH = "${COMMENTS_PATH}/getcomments/{tid}"
const val CREATE_COMMENT_PATH = "${COMMENTS_PATH}/createcomment/{tid}"
const val DELETE_COMMENT_PATH = "${COMMENTS_PATH}/deletecomment/{cid}"