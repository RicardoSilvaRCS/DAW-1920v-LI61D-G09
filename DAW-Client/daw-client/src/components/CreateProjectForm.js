import React from 'react';
import { Form, Message, Container } from 'semantic-ui-react'



class CreateProjectForm extends React.Component {
    
    state = {
        projname: '',
        descr: '',
        labels: [],
        initstate: '',
        transitions: [],
        final: false,
        error: ''
    }
    
    /**
     * Since setState is asynchronous we pass a callback for our login function soo that when we try and login the state is updated correctly
     * In this stage we dont need anything from the response content, we just need to know the status code
     */
    handleSubmit = () => {
        this.setState({ final: true }, this.handleProjCreation)
    }

    async handleProjCreation() {
        if(!this.state.final) {
            this.setState({error: 'Unexpected error occured. Sorry for the inconvenience please try again later.'})
            return
        }
        console.log("handleProjCreation func")
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

    handleArrayChange = (e, {name, value}) => {
        this.state[name].push(value)
    }

  render() {
    const {projname, descr, labels, initstate, transitions} = this.state

      return (
        <Container text>
            <Form onSubmit={this.handleSubmit}>
                {this.state.error && 
                    <Message negative>
                        <Message.Header>{this.state.error}</Message.Header>
                    </Message>
                }
                <Form.Input required icon='user' iconPosition='left' placeholder='Name' name='name' value={projname} onChange={this.handleChange}/>
                <Form.Input required icon='keyboard' iconPosition='left' placeholder='Description' name='descr' value={descr} onChange={this.handleChange}/>
                
                <p>We need to user elements for the labels and states. Like special boxes with a + square to add more labels and states.
                    We also need to make sure there is allways the last transition of "closed->archived" in those options
                </p>

                <Form.Button content='Create Project' />
            </Form>
        </Container>
      )
  }

}
  
export default CreateProjectForm