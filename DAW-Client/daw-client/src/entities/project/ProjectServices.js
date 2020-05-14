import ServerInfo from '../../serverinfo/ServerInfo'
import ProjectPaths from './ProjectPaths'

const host = ServerInfo.serverHost

async function getProjectsOfUser(username) {
    console.log(`ProjectServices.getProjectsOfUser()`)
    const getProjOfUserResponse = await fetch(`http://${host}${ProjectPaths.getProjectsOfUserPath(username)}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json'}
    })
    return getProjOfUserResponse
} 


async function createProjectOfUser(username, projectCreationDataModel) {
    console.log(projectCreationDataModel)
    console.log(`ProjectServices.createProjectsOfUser()`)
    const getProjOfUserResponse = await fetch(`http://${host}${ProjectPaths.createProjectOfUser(username)}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify(projectCreationDataModel)
    })
    return getProjOfUserResponse
} 

async function getProjectDetails(projName){
    console.log(`ProjectServices.getProjectDetails()`)
    const getProjDetailResponse = await fetch(`http://${host}${ProjectPaths.getProjectcDetails(projName)}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json'}
    })
    return getProjDetailResponse
}

async function deleteProject(projName){
    console.log(`ProjectServices.deleteProject()`)
    const deleteProjectResponse = await fetch(`http://${host}${ProjectPaths.deleteProject(projName)}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json'}
    })
    return deleteProjectResponse
}

export default {
    getProjectsOfUser,
    createProjectOfUser,
    getProjectDetails,
    deleteProject
}