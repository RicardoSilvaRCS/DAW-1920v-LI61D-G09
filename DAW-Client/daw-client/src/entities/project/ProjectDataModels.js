function projectsPrevDataModel(name, descri) {
    return {
        projectname: name,
        projectdescr: descri
    }
}

//If something changes here you must check the CreateProjectFrom state to see if u need to change something
function projectCreationDataModel(projname, descr, labels, initstate, transitions) {

    let labelsFormat = []
    //In the API labels are an Array<String>
    for(let i = 0; i<labels.length; i++) {
        labelsFormat.push(labels[i].labelvalue)
    }
    //In the API transitions are an Array<Pair<String,String>>
    let transitionsFormat = []
    for(let i = 0; i<transitions.length; i++) {
        transitionsFormat.push({
            first: transitions[i].starttrans,
            second: transitions[i].endtrans
        })
    }

    return {
        name: projname,
        descr: descr,
        labels: labelsFormat,
        initstate: initstate,
        transitions: transitionsFormat
    }
}

/** 
 * descr -> String
*/
function updateIssueDataModel (descr) {
    return {
        newDescr : descr
    }
}

export default {
    projectsPrevDataModel,
    projectCreationDataModel,
    updateIssueDataModel
}