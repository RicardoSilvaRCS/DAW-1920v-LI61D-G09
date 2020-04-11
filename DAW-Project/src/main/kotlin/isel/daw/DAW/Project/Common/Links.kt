package isel.daw.DAW.Project.Common

/**
 * All paths on the API
 *
 *  TODO: Perguntar se em projetos maiores compensa colocar os nomes do endpoints identicos ao nome das pastas
 *        para ser de mais facil resolução em caso de erro
 */

/*HOME PATH*/
const val PROJECT_MANAGER_PATH = "/home"

/*PROJECT PATHS*/
const val PROJECTS_PATH = "/projects"
const val GET_PROJECTS_PATH = "$PROJECTS_PATH"
const val GET_SINGLE_PROJECT_PATH = "$PROJECTS_PATH/{pname}"
const val CREATE_PROJECT_PATH = PROJECTS_PATH
const val UPDATE_PROJECT_PATH = "$PROJECTS_PATH/{pname}"
const val DELETE_PROJECT_PATH = "$PROJECTS_PATH/{pname}"

/*ISSUE PATHS*/
const val ISSUES_PATH = "/issues"
const val GET_ISSUES_PATH = "$ISSUES_PATH/{pname}"
const val GET_SINGLE_ISSUE_PATH = "/issue/{tid}"
const val CREATE_ISSUE_PATH = "$ISSUES_PATH"
const val UPDATE_ISSUE_PATH = "$ISSUES_PATH/{tid}"
const val UPDATE_ISSUE_STATE_PATH = "${UPDATE_ISSUE_PATH}/updatestate"
const val DELETE_ISSUE_PATH = "$ISSUES_PATH/{tid}"

/*COMMENT PATHS*/
const val COMMENTS_PATH = "/comments"
const val GET_COMMENTS_PATH = "$COMMENTS_PATH/{tid}"
const val CREATE_COMMENT_PATH = "$COMMENTS_PATH/{tid}"
const val UPDATE_COMMENT_PATH = "$COMMENTS_PATH/{cid}"
const val DELETE_COMMENT_PATH = "$COMMENTS_PATH/{cid}"