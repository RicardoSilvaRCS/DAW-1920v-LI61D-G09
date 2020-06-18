import React from 'react';
import { Form, Message, Dropdown, Container, FormField, FormGroup } from 'semantic-ui-react'
import IssueServices from '../IssueServices'
import IssueDataModel from '../IssuesDataModel'


/**
 * For now it's updating  Issues state in Proect10 from user Joao, but we need to do the auth "global state" or something to create it on the user that is authenticated
 */
class UpdateIssueStateForm extends React.Component {

    handleSubmit = () => {
        this.setState({ final: true }, this.handleUpdateIssueState)
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

    handleStateChange = (e, { name, value }) => {
        this.setState(state => {
            state.selectedState = value
            return state    
        })
    }



    async handleUpdateIssueState () {

        if(!this.state.selectedState){
            this.setState({error: 'Please Select A State.'})
            return
        }

        if(!this.state.final) {
            this.setState({error: 'Unexpected error occured. Sorry for the inconvenience please try again later.'})
            return
        }

        const updateIssueStateResponse = await IssueServices
            .updateIssueState(
                this.state.issueId,
                IssueDataModel.updateIssueState(this.state.selectedState)
            )
        const updateIssueStateContent = await updateIssueStateResponse.json()    

        if(updateIssueStateResponse.status === 200) {
            this.setState({message: `Issue State Updated`}, this.handleStateReset)
        } else {
            this.setState({error: updateIssueStateContent.properties.detail})
        }
    }

    handleStateReset() {
        this.setState({
            issuename: '',
            issueId: '',
            currState: '',
            possibleNextStates:[],
            final: false,
        })
    }

    
    handleDismissMessage = () => {
        this.setState({ message: null })
    }

    handleDismissError = () => {
        this.setState({ error: null })
    }

    constructor(props) {
        super(props)
        const issue = this.props.issue
        this.state = {
            issueName: issue.name,
            issueId : issue.id,
            currState : issue.currState,
            possibleNextStates : issue.possibleNextStates,
            final: false,
            error: null,
            message: null,
            selectedState : ""
        }
    }

  render() {
    const {issuename, issueId, currState, possibleNextStates} = this.state
   
    let availableStates = possibleNextStates.map((state)=>{
        return {
            key: state,
            text: state,
            value: state
        }
    })

    return (
        <Container text>
            <Form onSubmit={this.handleSubmit}>
                {this.state.message && 
                    <Message onDismiss={this.handleDismissMessage}>
                        <Message.Header>{this.state.message}</Message.Header>
                    </Message>
                }
                {this.state.error && 
                    <Message negative onDismiss={this.handleDismissError}>
                        <Message.Header>{this.state.error}</Message.Header>
                    </Message>
                }
                
                <FormField>
                    <h4>Here you can select from the available states:</h4>
                </FormField>
                
               
                    {availableStates.length > 0 && 
                        <FormGroup>
                            <Dropdown placeholder="Select a state" selection key={this.state} name="selectedState" options={availableStates} onChange={this.handleStateChange}/>
                            <Form.Button primary icon="save"/>
                        </FormGroup>
                    }
                    {availableStates.length <= 0 && 
                        <FormGroup>
                            <Dropdown placeholder="No More States" selection key="noMore" name="No More States" options={availableStates}/>
                            <Form.Button primary icon="save" disabled="true"/>
                        </FormGroup>
                    }
                
                
            </Form>
        </Container>
      )
  }

}
  
export default UpdateIssueStateForm