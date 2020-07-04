import React from 'react';
import { Form, Message} from 'semantic-ui-react'
import IssueServices from '../IssueServices'
import IssueDataModel from '../IssuesDataModel'
import { AppContext } from '../../../context/AppContext';


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
            message: null,
            final : false
        }
    }

    handleSubmit = () => {
        this.setState({ final: true }, this.handleIssueUpdate)
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

    async handleIssueUpdate () {
        const issueToUpdate = this.state

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
            ),
            this.context.authToken
        )

        const updateIssueContent = await updateIssueResponse.json()

        if(updateIssueResponse.status === 200) {
            this.setState({message: `Issue updated`}, this.handleStateReset)
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
        this.setState({
            name: '',
            descr: '',
            final : false
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
            {!this.state.final &&
                <Form.Button primary icon="save" />
            }
            {this.state.final &&
                <Form.Button basic loading/>
            }
        </Form>
      )
  }

}
  
CreateIssueForm.contextType = AppContext

export default CreateIssueForm