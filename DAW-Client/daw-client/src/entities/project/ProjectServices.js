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

async function getProjectsDetails(projName){
    const getProjDetailResponse = await fetch(`http://${host}${ProjectPaths.getProjectcDetails(projName)}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json'}
    })
    return getProjDetailResponse
}

export default {
    getProjectsOfUser,
    createProjectOfUser,
    getProjectsDetails
}