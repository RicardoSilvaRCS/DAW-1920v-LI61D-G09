/**
 * TODO: Maybe put here the request information for the server
 */
module.exports = {
    homePath: () => {return `/home`},
    findUsersPath: (startName) => {return `/user/userlist/${startName}`},
    getUserDetailsPath: (userName) => {return `/user/${userName}`},
    registerPath: "/user/register",
    updateUserPath: "/user",
    deleteUserPath: (userName) => {return `/user/${userName}`},
    loginPath: "/user/login",
    logoutPath: "/user/logout",
    addUserToProjPath: (userName, projName) => {return `/user/${userName}/project/${projName}`},
    getUserFriendsPath: (userName) => {return `/user/${userName}/friendslist`},
    addUserToFriendsPath: (userName, friendName) => {return `/user/${userName}/friendslist/${friendName}`},
}