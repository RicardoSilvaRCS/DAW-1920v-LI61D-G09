/**
 * TODO: Maybe put here the request information for the server
 */
module.exports = {
    getProjectsOfUserPath: (username) => { return `/projects/user/${username}`},
    getProjectDetailsPath: (projName) => { return `/projects/${projName}`},
    createProjectOfUserPath: (username) => { return `/projects/${username}`},
    updateProjectOfUserPath: (projname) => {return `/projects/${projname}`},
    deleteProjectPath: (projName) => {return `/projects/${projName}`},
    createProjectLabelPath: (projName, labelName) => {return `/projects/${projName}/label/${labelName}`},
    deleteProjectLabelPath: (projName, labelName) => {return `/projects/${projName}/label/${labelName}`},
}