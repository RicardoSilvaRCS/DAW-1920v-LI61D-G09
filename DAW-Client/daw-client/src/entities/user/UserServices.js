import ServerInfo from '../../serverinfo/ServerInfo'
import UserPaths from './UserPaths'

const host = ServerInfo.serverHost

async function getHome() {
    const getHomeResponse = await fetch(`http://${host}${UserPaths.homePath()}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    return getHomeResponse
} 

async function findUsers(startName) {
    const findUsersResponse = await fetch(`http://${host}${UserPaths.findUsersPath(startName)}`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
    return findUsersResponse
} 

async function getUserDetails(userName, authorizationToken) {
    const getUserDetailsResponse = await fetch(`http://${host}${UserPaths.getUserDetailsPath(userName)}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': authorizationToken
        }
    })
    return getUserDetailsResponse
} 

async function registerUser(registerDataModel) {
    const registerResponse = await fetch(`http://${host}${UserPaths.registerPath}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(registerDataModel)
    })
    return registerResponse
} 

async function updateUser(updateUserDataModel, authorizationToken) {
    throw (`TODO: Implement UserServices.updateUser(${updateUserDataModel})`)
}

async function deleteUser(userName, authorizationToken) {
    throw (`TODO: Implement UserServices.deleteUser(${userName})`)
}

async function loginUser(loginDataModel) {
    const loginResponse = await fetch(`http://${host}${UserPaths.loginPath}`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(loginDataModel)
    })
    return loginResponse
} 

async function logoutUser(logoutDataModel) {
    throw (`TODO: Implement UserServices.logoutUser(${logoutDataModel})`)
}

async function addUserToProject(addUserToProjectDataModel, authorizationToken) {
    throw (`TODO: Implement UserServices.addUserToProject(${addUserToProjectDataModel})`)
}

async function getFriendslist(userName, authorizationToken) {
    throw (`TODO: Implement UserServices.getFriendslist(${userName})`)
}

async function addFriend(addFriendDataModel, authorizationToken) {
    throw (`TODO: Implement UserServices.addFriend(${addFriendDataModel})`)
}


export default {
    findUsers,
    getUserDetails,
    registerUser,
    updateUser,
    deleteUser,
    loginUser,
    logoutUser,
    addUserToProject,
    getFriendslist,
    addFriend,
    getHome
}