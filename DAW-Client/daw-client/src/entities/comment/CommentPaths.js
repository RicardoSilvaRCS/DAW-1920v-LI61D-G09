/**
 * TODO: Maybe put here the request information for the server
 */
module.exports = {
    getIssueCommentsPath: (issueId) => {return `/comments/${issueId}`},
    createCommentPath: (issueId) => {return `/comments/${issueId}`},
    updateCommentPath: (issueId) => {return `/comments/${issueId}`},
    deleteCommentPath: (issueId) => {return `/comments/${issueId}`},
}