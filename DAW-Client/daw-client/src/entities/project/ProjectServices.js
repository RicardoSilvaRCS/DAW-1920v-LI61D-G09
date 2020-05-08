import ServerInfo from '../../serverinfo/ServerInfo'
import ProjectPaths from './ProjectPaths'

const host = ServerInfo.serverHost

async function getProjectsOfUser(username) {
    console.log(`ProjectServices.getProjectsOfUser()`)
    const getProjOfUserResponse = await fetch(`http://${host}${ProjectPaths.getProjectsOfUserPath.replace("{username}", username)}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    })
    const content = await getProjOfUserResponse.json()
    return content
} 

export default {
    getProjectsOfUser
}