/**
 * TODO: Maybe put here the request information for the server
 */
module.exports = {
    getProjectsOfUserPath: (username) => { return `/projects/user/${username}`},
    createProjectOfUser: (username) => { return `/projects/${username}`},
    getProjectcDetails: (projName) => { return `/projects/${projName}`},
    deleteProject: (projName) => {return `/projects/${projName}`}
}