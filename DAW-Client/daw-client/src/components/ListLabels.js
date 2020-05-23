import React from 'react';
import { List,Label } from 'semantic-ui-react'

function listLabels ({labels}) {
    console.log(labels)
    return(
        <List.Item>
            <List.Content>
                <Label color="blue" horizontal content="Labels" icon="tags" size="large" />
                    {labels.length <= 0 && (
                        <List.Description>No labels.</List.Description>     
                    )}
                    {labels.length > 0 && (
                        <List.List>
                            {labels.map((label) => (
                                <List.Item key={`${label}`}>
                                    <List.Icon name='tag' size='small' verticalAlign='middle' />
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