import ServerInfo from '../../serverinfo/ServerInfo'
import UserPaths from './UserPaths'

const host = ServerInfo.serverHost

async function loginUser(loginDataModel) {
    console.log(`UserService.loginUser()`)
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

async function registerUser(registerDataModel) {
    console.log(`UserService.registerUser()`)
    const registerResponse = await fetch(`http://${host}${UserPaths.registerPath}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(registerDataModel)
    })
    return registerResponse
} 

export default {
    loginUser,
    registerUser
}