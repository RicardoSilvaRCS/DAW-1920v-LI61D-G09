/**
 * issueName -> String
 * projName-> String
 * descr -> String
 * currState -> String
 * Labels -> String []
 * 
*/
function createIssueDataModel (issueName, projName, descr, currState, labels) {

    let labelsFormat = []
    for(let i = 0; i<labels.length; i++) {
        labelsFormat.push(labels[i].labelvalue)
    }

    return {
        name : issueName,
        projname : projName,
        descr : descr,
        currState : currState,
        labels : labelsFormat
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
    createIssueDataModel,
    updateIssueState,
    createIssueLabel
}