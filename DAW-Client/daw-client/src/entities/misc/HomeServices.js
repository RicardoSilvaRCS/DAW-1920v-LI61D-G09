import ServerInfo from '../../serverinfo/ServerInfo'

const host = ServerInfo.serverHost

async function getHome() {
    const getHomeResponse = await fetch(`http://${host}/home`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
    return getHomeResponse
}

export default {
    getHome
}