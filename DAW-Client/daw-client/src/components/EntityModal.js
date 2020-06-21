import React from 'react';
import { Button, Modal } from 'semantic-ui-react'

class EntityModal extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            header : props.header,
            entity: props.entity,
            icon : props.icon,
            floated : props.floated,
            size : props.size
        }
        
    }

    async handleClose() {
        window.location.reload(true);
    }

    render() {
        return (
            <Modal 
                dimmer="blurring"  
                trigger={<Button icon={this.state.icon || "wheelchair"} basic floated={this.state.floated || "right"} size={this.state.size || "tiny"} compact></Button>} 
                style={
                    {height:"auto" , top: "auto", left: "auto", right: "auto", bottom: "auto"}
                }
                onClose={this.handleClose}>
                <Modal.Header>{this.state.header ||"Update"} {this.state.entity}</Modal.Header>
                <Modal.Content scrolling={true}>
                    {this.props.children}
                </Modal.Content>
            </Modal>
        )
    }

}
  
export default EntityModal