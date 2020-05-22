import ServerInfo from '../../serverinfo/ServerInfo'
import ProjectPaths from './ProjectPaths'

const host = ServerInfo.serverHost

async function getProjectsOfUser(username) {
    console.log(`ProjectServices.getProjectsOfUser(${username})`)
    const getProjOfUserResponse = await fetch(`http://${host}${ProjectPaths.getProjectsOfUserPath(username)}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json'}
    })
    return getProjOfUserResponse
} 

async function getProjectDetails(projName){
    console.log(`ProjectServices.getProjectDetails()`)
    const getProjDetailResponse = await fetch(`http://${host}${ProjectPaths.getProjectDetailsPath(projName)}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json'}
    })
    return getProjDetailResponse
}

async function createProjectOfUser(username, projectCreationDataModel) {
    console.log(`ProjectServices.createProjectsOfUser(${username},${projectCreationDataModel})`)
    const getProjOfUserResponse = await fetch(`http://${host}${ProjectPaths.createProjectOfUserPath(username)}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify(projectCreationDataModel)
    })
    return getProjOfUserResponse
} 

async function updateProjectOfUser(projName, projectUpdateDataModel) {
    //throw (`TODO: ProjectServices.updateProjectsOfUser()`)
    console.log("ProjectServices.updateProjectsOfUser()")
    const updateProjectResponse = await fetch(`http://${host}${ProjectPaths.updateProjectOfUserPath(projName)}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify(projectUpdateDataModel)
    })
    return updateProjectResponse
} 


async function deleteProjectOfUser(projName){
    console.log(`ProjectServices.deleteProject()`)
    const deleteProjectResponse = await fetch(`http://${host}${ProjectPaths.deleteProjectPath(projName)}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json'}
    })
    return deleteProjectResponse
}

async function createProjectLabel() {
    throw (`TODO: ProjectServices.createProjectLabel()`)
} 

async function deleteProjectLabel() {
    throw (`TODO: ProjectServices.deleteProjectLabel()`)
} 

export default {
    getProjectsOfUser,
    getProjectDetails,
    createProjectOfUser,
    updateProjectOfUser,
    deleteProjectOfUser,
    createProjectLabel,
    deleteProjectLabel
}