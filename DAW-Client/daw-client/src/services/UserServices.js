import ServerInfo from '../serverinfo/ServerInfo'
import UserPaths from '../serverinfo/serverpaths/UserPaths'

const host = ServerInfo.serverHost

async function loginUser(username, password) {
    console.log(`UserService.loginUser()`)
    const loginResponse = await fetch(`http://${host}${UserPaths.loginPath}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(
            { 
                username: username,
                password: password
            }
        )
    })
    const content = await loginResponse.json()
    console.log(content)
    return content
} 

async function registerUser({username, fullname, email, age, gender, phonenumber, password}) {
    console.log(`UserService.registerUser()`)
    const registerResponse = await fetch(`http://${host}${UserPaths.registerPath}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(
            {
                username: username,
                fullname: fullname,
                email: email,
                age: age,
                gender: gender,
                phonenumber: phonenumber,
                password: password
            }
        )
    })
    const content = await registerResponse.json()
    console.log(content)
    return content
} 

export default {
    loginUser,
    registerUser
}