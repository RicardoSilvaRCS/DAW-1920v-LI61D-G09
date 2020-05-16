import React from 'react';
import { List } from 'semantic-ui-react'

function listStates ({states}){
    return (
        <List.Item key="StateList">
            <List.Icon name="angle double right"/>
                <List.Content>
                    <List.Header>Possible States:</List.Header>
                        {states.length <= 0 && (
                            <List.Description>No states.</List.Description>     
                        )}

                        {states.length > 0 && (
                            <List.List>
                                {states.map((state) => (
                                    <List.Item key={`${state}`}>
                                        <List.Icon name='dot circle' size='tiny' verticalAlign='middle' />
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