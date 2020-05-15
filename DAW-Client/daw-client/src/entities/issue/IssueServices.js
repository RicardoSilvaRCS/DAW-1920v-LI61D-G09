import ServerInfo from '../../serverinfo/ServerInfo'
import IssuePaths from './IssuePaths'

const host = ServerInfo.serverHost

async function getProjectIssues(projectName) {
    const getProjectIssuesResponse = await fetch(`http://${host}${IssuePaths.getProjectIssues.replace("{pname}", projectName)}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json'}
    })
    return getProjectIssuesResponse
} 

async function getIssueDetails(issueId){
    const getIssueDetailsReponse = await fetch(`http://${host}${IssuePaths.getSingleIssue.replace("{tid}", issueId)}`,{
        method: 'GET',
        headers: { 'Content-Type': 'application/json'}
    })
    return getIssueDetailsReponse
}

async function createIssue(createIssueDataModel){
    const createIssueReponse = await fetch(`http://${host}${IssuePaths.createIssue}`,{
        method: 'POST',
        body : JSON.stringify(createIssueDataModel),
        headers: { 'Content-Type': 'application/json'}
    })
    return createIssueReponse
}

async function updateIssue(issue){
    const updateIssueReponse = await fetch(`http://${host}${IssuePaths.updateIssue.replace("{tid}",issue.id)}`,{
        method: 'PUT',
        body : JSON.stringify(issue),
        headers: { 'Content-Type': 'application/json'}
    })

    return updateIssueReponse
}

async function deleteIssue(issueId){
    const deleteIssueReponse = await fetch(`http://${host}${IssuePaths.deleteIssue.replace("{tid}",issueId)}`,{
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json'}
    })

    return deleteIssueReponse
}

async function updateIssueState(issue){
    const updateIssueStateReponse = await fetch(`http://${host}${IssuePaths.updateIssueState.replace("{tid}",issue.id)}`,{
        method: 'PUT',
        body : JSON.stringify(issue),
        headers: { 'Content-Type': 'application/json'}
    })

    return updateIssueStateReponse
}

async function createIssueLabel(issueId, label){
    const createIssueLabelResponse = await fetch(`http://${host}${IssuePaths.createIssue.replace("{tid}",issueId)}`,{
        method: 'POST',
        body : JSON.stringify(label),
        headers: { 'Content-Type': 'application/json'}
    })

    return createIssueLabelResponse
}

async function deleteIssueLabel(issueId, labelName){
    const deleteIssueReponse = await fetch(`http://${host}${IssuePaths.deleteIssueLabel.replace("{tid}",issueId).replace("{labelName}",labelName)}`,{
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json'}
    })

    return deleteIssueReponse
}


export default {
    getProjectIssues,
    getIssueDetails,
    createIssue,
    updateIssue,
    deleteIssue,
    updateIssueState,
    createIssueLabel,
    deleteIssueLabel
}