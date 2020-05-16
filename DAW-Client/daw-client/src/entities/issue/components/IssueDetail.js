import React from 'react';
import { Container,List } from "semantic-ui-react";

/*Components*/
import ListStatesComponent from '../../../components/ListStates'
import ListLabelsComponent from '../../../components/ListLabels'

function representIssueDetails ({issue}) {
    const creationDate = formatDate(new Date(issue.creationDate))
    const updateDate = formatDate(new Date(issue.updateDate))
    const closeDate = formatDate(new Date(issue.closeDate))
    return(
        <Container>
            <List>
                <List.Item>
                    <List.Icon name="keyboard"/>
                    <List.Content>
                        <List.Header>Name: </List.Header>
                        <List.Description>{issue.name}</List.Description>
                    </List.Content>
                </List.Item>
                <List.Item>
                        <List.Icon name="keyboard"/>
                            <List.Content>
                                <List.Header>Creation Date: </List.Header>
                                <List.Description>{creationDate}</List.Description>
                            </List.Content>
                </List.Item>
                <List.Item>
                        <List.Icon name="keyboard"/>
                            <List.Content>
                                <List.Header>Update Date: </List.Header>
                                <List.Description>{updateDate}</List.Description>
                            </List.Content>
                </List.Item>
                {issue.closeDate &&
                    <List.Item>
                        <List.Icon name="keyboard"/>
                            <List.Content>
                                <List.Header>Close Date: </List.Header>
                                <List.Description>{closeDate}</List.Description>
                            </List.Content>
                    </List.Item>
                }{!issue.closeDate &&
                    <List.Item>
                        <List.Icon name="keyboard"/>
                        <List.Content>
                            <List.Header>Close Date: </List.Header>
                                <List.Description>-----</List.Description>
                        </List.Content>
                    </List.Item>
                }
                <List.Item>
                    <List.Icon name="keyboard"/>
                        <List.Content>
                            <List.Header>Description:</List.Header>
                            <List.Description>{issue.descr}</List.Description>
                        </List.Content>
                    </List.Item>
                    <List.Item>
                        <List.Icon name="keyboard"/>
                        <List.Content>
                            <List.Header href={`/projects/${issue.projname}/details`}>Project Name:</List.Header>
                            <List.Description>{issue.descr}</List.Description>
                        </List.Content>
                    </List.Item>
                    <ListLabelsComponent labels={issue.labels}/>
                        <List.Item>
                            <List.Icon name='caret right'/>
                            <List.Content>
                                <List.Header>Current State:</List.Header>
                                <List.Description>{issue.currState}</List.Description>
                            </List.Content>
                </List.Item>
                <ListStatesComponent states = {issue.possibleNextStates}/>
            </List>
        </Container>
    )
}


function formatDate(date){
    return `${date.getDay()}/${date.getMonth()}/${date.getFullYear()}`
}
export default representIssueDetails