import React from 'react';
import { List, Label, Button, Icon } from 'semantic-ui-react'

function listLabelsWithDelete ({labels, handlerDelete}) {
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
                                    <List.Content>
                                        <List.Description>
                                            <Label labelName={label} onRemove={handlerDelete} content={label} tag></Label>
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

export default listLabelsWithDelete