import React from 'react';
import { Form, Message} from 'semantic-ui-react'
import IssueServices from '../IssueServices'
import IssueDataModel from '../IssuesDataModel'


class CreateIssueForm extends React.Component {

    constructor(props) {
        super(props)
        const issue = this.props.issue
        this.state = {
            id : issue.id,
            name : "",
            projname: issue.projname,
            descr : "",
            error: null,
            message: null
        }
    }

    handleSubmit = () => {
        this.setState({ final: true }, this.handleIssueUpdate)
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

    async handleIssueUpdate () {
        const issueToUpdate = this.state
        console.log(issueToUpdate)

        if(!issueToUpdate.name && !issueToUpdate.descr){
            this.setState({error: 'Please insert something to update'})
            return
        }

        const updateIssueResponse = await IssueServices.updateIssue(
            issueToUpdate.id,
            IssueDataModel.updateIssueDataModel(
                issueToUpdate.name,
                issueToUpdate.projname,
                issueToUpdate.descr
            )
        )

        const updateIssueContent = await updateIssueResponse.json()
        
        console.log("this is the response status "+ updateIssueContent.status)

        if(updateIssueResponse.status === 200) {
            this.setState({message: `Issue ${updateIssueContent.properties.id} updated`}, this.handleStateReset)
        } else {
            this.setState({error: updateIssueContent.properties.detail})
        }
    }

    handleDismissMessage = () => {
        this.setState({ message: null })
    }

    handleDismissError = () => {
        this.setState({ error: null })
    }

    handleStateReset() {
        console.log("hey")
        this.setState({
            issuename: '',
            projname: '',
            descr: '',
        })
    }

  render() {
      const {name, projname, descr} = this.state
      return (
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

            <Form.Input fluid name="name" value={name} label='Issue Name' placeholder='Issue Name' onChange={this.handleChange}/>
            
            <Form.TextArea name="descr" value={descr} label='Description' placeholder='Write your description' onChange={this.handleChange} />
            
            <Form.Button primary icon="save" />
        </Form>
      )
  }

}
  
export default CreateIssueForm