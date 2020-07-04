import ServerInfo from '../../serverinfo/ServerInfo'
import ProjectPaths from './ProjectPaths'

const host = ServerInfo.serverHost

async function getProjectsOfUser(username, authorizationToken) {
    console.log(`ProjectServices.getProjectsOfUser(${username})`)
    const getProjOfUserResponse = await fetch(`http://${host}${ProjectPaths.getProjectsOfUserPath(username)}`, {
        method: 'GET',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': authorizationToken
        }
    })
    return getProjOfUserResponse
} 

async function getProjectDetails(projName, authorizationToken){
    console.log(`ProjectServices.getProjectDetails()`)
    const getProjDetailResponse = await fetch(`http://${host}${ProjectPaths.getProjectDetailsPath(projName)}`, {
        method: 'GET',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': authorizationToken
        }
    })
    return getProjDetailResponse
}

async function createProjectOfUser(username, projectCreationDataModel, authorizationToken) {
    console.log(`ProjectServices.createProjectsOfUser(${username},${projectCreationDataModel})`)
    const getProjOfUserResponse = await fetch(`http://${host}${ProjectPaths.createProjectOfUserPath(username)}`, {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': authorizationToken
        },
        body: JSON.stringify(projectCreationDataModel)
    })
    return getProjOfUserResponse
} 

async function updateProjectOfUser(projName, projectUpdateDataModel, authorizationToken) {
    console.log("ProjectServices.updateProjectsOfUser()")
    const updateProjectResponse = await fetch(`http://${host}${ProjectPaths.updateProjectOfUserPath(projName)}`, {
        method: 'PUT',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': authorizationToken
        },
        body: JSON.stringify(projectUpdateDataModel)
    })
    return updateProjectResponse
} 


async function deleteProjectOfUser(projName, authorizationToken){
    console.log(`ProjectServices.deleteProject()`)
    const deleteProjectResponse = await fetch(`http://${host}${ProjectPaths.deleteProjectPath(projName)}`, {
        method: 'DELETE',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': authorizationToken
        }
    })
    return deleteProjectResponse
}

async function createProjectLabel(projName, labelName, authorizationToken) {
    console.log("ProjectServices.createProjectLabel()")
    const createProjectLabelResponse = await fetch(`http://${host}${ProjectPaths.createProjectLabelPath(projName, labelName)}`, {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': authorizationToken
        },
    })
    return createProjectLabelResponse
} 

async function deleteProjectLabel(projName, labelName, authorizationToken) {
    console.log("ProjectServices.deleteProjectLabel()")
    const deleteProjectLabelResponse = await fetch(`http://${host}${ProjectPaths.deleteProjectLabelPath(projName, labelName)}`, {
        method: 'DELETE',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': authorizationToken
        },
    })
    return deleteProjectLabelResponse
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