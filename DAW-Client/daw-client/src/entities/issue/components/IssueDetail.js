import React from 'react';
import {List,Label,Icon} from "semantic-ui-react";

/*Components*/
import ListStatesComponent from '../../../components/ListStates'
import ListLabelsComponent from '../../../components/ListLabels'
import formatDate from '../../AuxiliarModules/DateFormat'
import UpdateIssueInfo from '../pages/UpdateIssueForm'
import UpdateEntityModal from '../../../components/EntityModal'
import UpdateIssueStateForm from '../../issue/pages/UpdateIssueStateForm'

function representIssueDetails ({issue}) {
    const creationDate = formatDate(new Date(issue.creationDate))
    const updateDate = formatDate(new Date(issue.updateDate))
    const closeDate = formatDate(new Date(issue.closeDate))

    return(
        <List divided selection>
            <List.Item>
                <Label color="blue" horizontal content="Name" icon="keyboard" size="large"/>
                {issue.name}

                <UpdateEntityModal entity="Update Issue Info" icon="pencil">
                                <UpdateIssueInfo issue={issue}/>
                </UpdateEntityModal>

            </List.Item>
            <List.Item>
                <Label color="blue" horizontal content="Creation Date" icon="calendar" size="large"/>
                {creationDate}
            </List.Item>
            <List.Item>
                <Label color="blue" horizontal content="Update Date" icon="calendar" size="large"/>
                {updateDate}
            </List.Item>
            {issue.closeDate &&
                <List.Item>
                    <Label color="blue" horizontal content="Close Date" icon="calendar" size="large"/>
                    {closeDate}
                </List.Item>
            }{!issue.closeDate &&
                <List.Item>
                    <Label color="blue" horizontal content="Close Date" icon="calendar" size="large"/>
                    --/--/----
                </List.Item>
            }
            <List.Item>
                <Label color="blue" horizontal content="Description" icon="keyboard" size="large"/>
                {issue.descr}
            </List.Item>
            <List.Item>
                <Label color="blue" horizontal content="Project Name" icon="calendar" size="large"/>
                <a href={`/projects/${issue.projname}/details`}>{issue.projname}</a>
            </List.Item>
                <ListLabelsComponent labels={issue.labels}/>
                <List.Item>
                    <Label color="blue" horizontal content="Current State" icon="play" size="large"/>
                    <UpdateEntityModal entity="Issue State" icon="pencil">
                                <UpdateIssueStateForm issue={issue}/>
                    </UpdateEntityModal>
                    {issue.currState}
                </List.Item>
            <ListStatesComponent states = {issue.possibleNextStates}/>
        </List>
    )
}

export default representIssueDetails