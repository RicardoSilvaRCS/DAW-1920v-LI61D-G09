import React from 'react';
import { List, Icon } from 'semantic-ui-react'

function listTransitions({transitions}){
    return(
        <List.Item>
            <List.Icon name="exchange"/>
            <List.Content>
                <List.Header>Possible State Transitions:</List.Header>
                    {transitions.length <= 0 && (
                        <List.Description>No transitions.</List.Description>     
                    )}
                    {transitions.length > 0 && (
                        <List.List>
                            {transitions.map((trans) => (
                                <List.Item key={`${trans.first}_${trans.second}`}>
                                    <List.Content>
                                        <List.Description>
                                            {trans.first}
                                            <Icon name="caret right"/>
                                                {trans.second}
                                        </List.Description>
                                    </List.Content>
                                </List.Item>
                            ))}
                        </List.List>   
                    )}
            </List.Content>
        </List.Item>
    )
}

export default listTransitions