import ServerInfo from '../../serverinfo/ServerInfo'
import UserPaths from './UserPaths'

const host = ServerInfo.serverHost

async function loginUser(loginDataModel) {
    console.log(`UserService.loginUser()`)
    const loginResponse = await fetch(`http://${host}${UserPaths.loginPath}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(loginDataModel)
    })
    const content = await loginResponse.json()
    return content
} 

async function registerUser(registerDataModel) {
    console.log(`UserService.registerUser()`)
    const registerResponse = await fetch(`http://${host}${UserPaths.registerPath}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(registerDataModel)
    })
    const content = await registerResponse.json()
    return content
} 

export default {
    loginUser,
    registerUser
}