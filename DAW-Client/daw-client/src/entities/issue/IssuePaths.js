/**
 * TODO: Maybe put here the request information for the server
 */
module.exports = {
    getProjectIssues: '/issues/{pname}',
    getSingleIssue: '/issues/{tid}' ,
    createIssue: '/issues',
    updateIssue : '/issues/{tid}',
    deleteIssue : '/issues/{tid}',
    updateIssueState : '/issues/updatestate',
    createIssueLabel : '/issues/{tid}/label',
    deleteIssueLabel : '/issues/{tid}/label/{labelName}'
}