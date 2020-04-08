package isel.daw.DAW.Project.Common

/**
 * All paths on the API
 *
 *  TODO: Perguntar se em projetos maiores compensa colocar os nomes do endpoints identicos ao nome das pastas
 *        para ser de mais facil resolução em caso de erro
 */

/*PROJECT PATHS*/
const val GET_PROJECTS_PATH = "/projects"
const val GET_SINGLE_PROJECT_PATH = "/projects/{pname}"
const val CREATE_PROJECT_PATH = "/projects"
const val UPDATE_PROJECT_PATH = "/projects/{pname}"
const val DELETE_PROJECT_PATH = "/projects/{pname}"

/*ISSUE PATHS*/
const val GET_ISSUES_PATH = "/issues/{pname}"
const val GET_SINGLE_ISSUE_PATH = "/issue/{tid}"
const val CREATE_ISSUE_PATH = "/issues"
const val UPDATE_ISSUE_PATH = "/issues/{tid}"
const val UPDATE_ISSUE_STATE_PATH = "${UPDATE_ISSUE_PATH}/updatestate"
const val DELETE_ISSUE_PATH = "/issues/{tid}"

/*COMMENT PATHS*/
const val GET_COMMENTS_PATH = "/comments/{tid}"
const val CREATE_COMMENT_PATH = "/comments/{tid}"
const val UPDATE_COMMENT_PATH = "/comments/{tid}"
const val DELETE_COMMENT_PATH = "/comments/{cid}"