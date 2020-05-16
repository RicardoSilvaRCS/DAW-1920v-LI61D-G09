import ServerInfo from '../../serverinfo/ServerInfo'
import UserPaths from './UserPaths'

const host = ServerInfo.serverHost

async function findUsers(startName) {
    console.log(`UserService.findUsers(${startName})`)
    const findUsersResponse = await fetch(`http://${host}${UserPaths.findUsersPath(startName)}`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
    return findUsersResponse
} 

async function getUserDetails(userName) {
    console.log(`UserService.getUserDetails(${userName})`)
    const getUserDetailsResponse = await fetch(`http://${host}${UserPaths.getUserDetailsPath(userName)}`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
    return getUserDetailsResponse
} 

async function registerUser(registerDataModel) {
    console.log(`UserService.registerUser(${registerDataModel})`)
    const registerResponse = await fetch(`http://${host}${UserPaths.registerPath}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(registerDataModel)
    })
    return registerResponse
} 

async function updateUser(updateUserDataModel) {
    throw (`TODO: Implement UserServices.updateUser(${updateUserDataModel})`)
}

async function deleteUser(userName) {
    throw (`TODO: Implement UserServices.deleteUser(${userName})`)
}

async function loginUser(loginDataModel) {
    console.log(`UserService.loginUser(${loginDataModel})`)
    const loginResponse = await fetch(`http://${host}${UserPaths.loginPath}`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(loginDataModel)
    })
    //Print dos headers recebidos
    for (var pair of loginResponse.headers.entries()) {
        console.log(pair[0]+ ': '+ pair[1]);
     }
    return loginResponse
} 

async function logoutUser(logoutDataModel) {
    throw (`TODO: Implement UserServices.logoutUser(${logoutDataModel})`)
}

async function addUserToProject(addUserToProjectDataModel) {
    throw (`TODO: Implement UserServices.addUserToProject(${addUserToProjectDataModel})`)
}

async function getFriendslist(userName) {
    throw (`TODO: Implement UserServices.getFriendslist(${userName})`)
}

async function addFriend(addFriendDataModel) {
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
    addFriend
}