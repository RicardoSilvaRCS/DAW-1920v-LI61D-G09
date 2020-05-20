import ServerInfo from '../../serverinfo/ServerInfo'
import IssuePaths from './IssuePaths'

const host = ServerInfo.serverHost

async function getProjectIssues(projectName) {
    console.log(`IssueServices.getProjectIssues(${projectName})`)
    const getProjectIssuesResponse = await fetch(`http://${host}${IssuePaths.getProjectIssuesPath(projectName)}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json'}
    })
    return getProjectIssuesResponse
} 

async function getIssueDetails(issueId){
    console.log(`IssueServices.getIssueDetails(${issueId})`)
    const getIssueDetailsReponse = await fetch(`http://${host}${IssuePaths.getSingleIssuePath(issueId)}`,{
        method: 'GET',
        headers: { 'Content-Type': 'application/json'}
    })
    return getIssueDetailsReponse
}

async function createIssue(createIssueDataModel){
    console.log(`IssueServices.createIssue(${createIssueDataModel})`)
    const createIssueReponse = await fetch(`http://${host}${IssuePaths.createIssuePath}`,{
        method: 'POST',
        body : JSON.stringify(createIssueDataModel),
        headers: { 'Content-Type': 'application/json'}
    })
    return createIssueReponse
}

async function updateIssue(issueId, updateIssueDataModel){
    console.log(`IssueServices.updateIssue(${issueId}, ${updateIssueDataModel})`)
    const updateIssueReponse = await fetch(`http://${host}${IssuePaths.updateIssuePath(issueId)}`,{
        method: 'PUT',
        body : JSON.stringify(updateIssueDataModel),
        headers: { 'Content-Type': 'application/json'}
    })

    return updateIssueReponse
}

async function deleteIssue(issueId){
    console.log(`IssueServices.deleteIssue(${issueId})`)
    const deleteIssueReponse = await fetch(`http://${host}${IssuePaths.deleteIssuePath(issueId)}`,{
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json'}
    })

    return deleteIssueReponse
}

async function updateIssueState(issueId, updateIssueStateDataModel){
    console.log(`IssueServices.updateIssueState(${updateIssueStateDataModel})`)
    const updateIssueStateReponse = await fetch(`http://${host}${IssuePaths.updateIssueStatePath(issueId)}`,{
        method: 'PUT',
        body : JSON.stringify(updateIssueStateDataModel),
        headers: { 'Content-Type': 'application/json'}
    })

    return updateIssueStateReponse
}

async function createIssueLabel(issueId, label){
    console.log(`IssueServices.createIssueLabel(${issueId}. ${label})`)
    const createIssueLabelResponse = await fetch(`http://${host}${IssuePaths.createIssueLabelPath(issueId)}`,{
        method: 'POST',
        body : JSON.stringify(label),
        headers: { 'Content-Type': 'application/json'}
    })

    return createIssueLabelResponse
}

async function deleteIssueLabel(issueId, labelName){
    console.log(`IssueServices.deleteIssueLabel(${issueId}. ${labelName})`)
    const deleteIssueReponse = await fetch(`http://${host}${IssuePaths.deleteIssueLabelPath(issueId, labelName)}`,{
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