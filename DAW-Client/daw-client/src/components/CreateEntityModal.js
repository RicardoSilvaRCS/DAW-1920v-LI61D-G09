import React from 'react';
import { Button, Form, Modal } from 'semantic-ui-react'

class CreateEntityModal extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            entity: props.entity
        }
        
    }

    render() {
        console.log(this.state.entity)
        return (
            <Modal 
                dimmer="blurring"  
                trigger={<Button>Add {this.state.entity}</Button>} 
                closeIcon
                style={
                    {height: "auto", top: "auto", left: "auto", right: "auto", bottom: "auto"}
                }>
                <Modal.Header>Create {this.state.entity}</Modal.Header>
                <Modal.Content scrolling={true}>
                    {this.props.children}
                </Modal.Content>
            </Modal>
        )
    }

}
  
export default CreateEntityModal