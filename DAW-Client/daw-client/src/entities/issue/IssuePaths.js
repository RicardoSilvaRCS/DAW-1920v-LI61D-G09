/**
 * TODO: Maybe put here the request information for the server
 */
module.exports = {
    getProjectIssuesPath: (projName) => { return `/issues/${projName}`},
    getSingleIssuePath: (issueId) => { return `/issue/${issueId}`},
    createIssuePath: '/issues',
    updateIssuePath: (issueId) => { return `/issues/${issueId}`},
    deleteIssuePath: (issueId) => { return `/issues/${issueId}`},
    updateIssueStatePath: (issueId) => {return `/issues/${issueId}/updatestate`},
    createIssueLabelPath: (issueId) => { return `/issues/${issueId}/label`},
    deleteIssueLabelPath: (issueId, labelName) => { return `/issues/${issueId}/label/${labelName}`}
}
