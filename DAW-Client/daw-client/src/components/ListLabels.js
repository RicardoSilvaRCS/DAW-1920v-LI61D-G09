import React from 'react';
import { List } from 'semantic-ui-react'

function listLabels ({labels}) {
    return(
        <List.Item>
            <List.Icon name="tags"/>
            <List.Content>
                <List.Header>Labels:</List.Header>
                    {labels.length <= 0 && (
                        <List.Description>No labels.</List.Description>     
                    )}
                    {labels.length > 0 && (
                        <List.List>
                            {labels.map((label) => (
                                <List.Item key={`${label}`}>
                                    <List.Icon name='dot circle' size='tiny' verticalAlign='middle' />
                                    <List.Content>
                                        <List.Description>{label}</List.Description>
                                    </List.Content>
                                </List.Item>
                            ))}
                        </List.List>   
                    )}
            </List.Content>
        </List.Item>
    )
}

export default listLabels