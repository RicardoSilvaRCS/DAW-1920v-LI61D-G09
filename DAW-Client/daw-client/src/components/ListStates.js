import React from 'react';
import { List,Label } from 'semantic-ui-react'

function listStates ({states}){
    return (
        <List.Item key="StateList">
                <List.Content>
                    <Label color="blue" horizontal content="Possible States" icon="angle double right" size="large"/>
                        {states.length <= 0 && (
                            <List.Description>No states.</List.Description>     
                        )}
                        {states.length > 0 && (
                            <List.List>
                                {states.map((state) => (
                                    <List.Item key={`${state}`}>
                                        <List.Icon name='angle right' size='small' verticalAlign='middle' />
                                        <List.Content>
                                            <List.Description>{state}</List.Description>
                                        </List.Content>
                                    </List.Item>
                                ))}
                            </List.List>   
                        )}
                </List.Content>
        </List.Item>
    )
}

export default listStates