/**
 * issueName -> String
 * projName-> String
 * descr -> String
 * currState -> String
 * Labels -> String []
 * 
*/
function createIssuePrevDataModel (issueName, projName, descr, currState, labels) {
    return {
        name : issueName,
        projname : projName,
        descr : descr,
        currState : currState,
        labels : labels
    }
}

/**
 * newState -> String
*/
function updateIssueState(newState){
    return {
        state : newState
    }
}


/**
 * labelName -> String
 * projName -> String
*/
function createIssueLabel(labelName , projName){
    return {
        labelName : labelName,
        projname : projName
    }
}

module.exports = {
    createIssuePrevDataModel,
    updateIssueState,
    createIssueLabel
}