import React from 'react';
import { List } from 'semantic-ui-react'

/**
 * 
 * @param {members} members is an array with objets of type {name, descr}  
 */

function ContentList({members, icon}) {
    return (
        <List size='huge' divided>
            {members.map( (it) => (
            <List.Item key={it.name}>
            <List.Icon name={icon} size='large' verticalAlign='middle' />
            <List.Content>
                <List.Header as='a' href="/">{it.name}</List.Header>
                <List.Description as='p'>{it.descr}</List.Description>
            </List.Content>
            </List.Item>
            ))}
        </List>
    )
  }
  
  export default ContentList