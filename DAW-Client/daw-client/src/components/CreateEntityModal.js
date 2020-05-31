import React from 'react';
import { Button, Modal } from 'semantic-ui-react'

class CreateEntityModal extends React.Component {

    //Talvez receber nos props a funçao de onClose para poder voltar a carregar os projetos/issues ou wtv de novo.
    constructor(props) {
        super(props)
        this.state = {
            entity: props.entity
        }
        
    }

    async handleClose() {
        
    }

    render() {
        return (
            <Modal 
                dimmer="blurring"  
                trigger={<Button>Add {this.state.entity}</Button>} 
                closeIcon
                style={
                    {height: "auto", top: "auto", left: "auto", right: "auto", bottom: "auto"}
                }
                onClose={this.handleClose}>
                <Modal.Header>Create {this.state.entity}</Modal.Header>
                <Modal.Content scrolling={true}>
                    {this.props.children}
                </Modal.Content>
            </Modal>
        )
    }

}
  
export default CreateEntityModal