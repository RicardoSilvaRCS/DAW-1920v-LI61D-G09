import React from 'react';
import { Form, Message, Dropdown, Container, Button, Icon } from 'semantic-ui-react'
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
            state.currState = value
            return state    
        })
    }



    async handleUpdateIssueState () {
        if(!this.state.final) {
            this.setState({error: 'Unexpected error occured. Sorry for the inconvenience please try again later.'})
            return
        }
        console.log(this.state)
        console.log(IssueDataModel.updateIssueState(this.state.currState))
        const updateIssueStateResponse = await IssueServices
            .updateIssueState(
                this.state.issueId,
                IssueDataModel.updateIssueState(this.state.currState)
            )
        const updateIssueStateContent = await updateIssueStateResponse.json()    
        
        console.log(updateIssueStateContent)

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
        //If something changes here you must check the ProjectDataModels.projectCreationDataModel() to see if u need to change something
        const issue = this.props.issue
        console.log(issue)
        this.state = {
            issueName: issue.name,
            issueId : issue.id,
            currState : issue.currState,
            possibleNextStates : issue.possibleNextStates,
            final: false,
            error: null,
            message: null
        }
    }

  render() {
    const {issuename, issueId, currState, possibleNextStates} = this.state
    const handleStateChange = this.handleStateChange

   
    const availableStates = possibleNextStates.map((state)=>{
        return {
            key: state,
            text: state,
            value: state
        }
    })

    console.log(availableStates)

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
                
                <p>Here you can select from the available states:</p>
                
                <Dropdown placeholder="Select a state" selection key={this.state} name={this.state} options={availableStates} onChange={handleStateChange}/>
                
                <Form.Button primary content='update Issue State'/>
            </Form>
        </Container>
      )
  }

}
  
export default UpdateIssueStateForm