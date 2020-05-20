import React from 'react';
import { Button, Modal } from 'semantic-ui-react'

class UpdateEntityModal extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            entity: props.entity
        }
        
    }

    async handleClose() {
        window.location.reload(false)
    }

    render() {
        return (
            <Modal 
                dimmer="blurring"  
                trigger={<Button>Update {this.state.entity}</Button>} 
                style={
                    {height:"auto" , top: "auto", left: "auto", right: "auto", bottom: "auto"}
                }
                onClose={this.handleClose}>
                <Modal.Header>Update {this.state.entity}</Modal.Header>
                <Modal.Content scrolling={true}>
                    {this.props.children}
                </Modal.Content>
            </Modal>
        )
    }

}
  
export default UpdateEntityModal