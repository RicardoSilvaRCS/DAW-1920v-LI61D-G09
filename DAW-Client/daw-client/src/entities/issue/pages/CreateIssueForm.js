import React from 'react';
import { Form, Message, Container, Button, Icon } from 'semantic-ui-react'
import IssueServices from '../IssueServices'
import IssuePaths from '../IssuePaths'


/**
 * For now it's creating Issues in Proect10 from user Joao, but we need to do the auth "global state" or something to create it on the user that is authenticated
 */
class CreateIssueForm extends React.Component {

    handleSubmit = () => {
        this.setState({ final: true }, this.handleIssueCreation)
    }

    async handleIssueCreation() {
        if(!this.state.final) {
            this.setState({error: 'Unexpected error occured. Sorry for the inconvenience please try again later.'})
            return
        }
    }

    constructor(props) {
        super(props)
        //If something changes here you must check the ProjectDataModels.projectCreationDataModel() to see if u need to change something
        this.state = {
            issuename: '',
            projname: '',
            descr: '',
            currState: '',
            labels: [{
                labelid: 0,
                labelvalue: ''
            }],
            final: false,
            error: null,
            message: null
        }
    }

  render() {
    const {issuename, projname, descr, currState, labels} = this.state

      return (
        <Container text>
            <Form onSubmit={this.handleSubmit}>
                {this.state.message && 
                    <Message>
                        <Message.Header>{this.state.message}</Message.Header>
                    </Message>
                }
                {this.state.error && 
                    <Message negative>
                        <Message.Header>{this.state.error}</Message.Header>
                    </Message>
                }
            </Form>
        </Container>
      )
  }

}
  
export default CreateIssueForm