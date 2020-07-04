import React from 'react';
import { Form, Message} from 'semantic-ui-react'
import ProjectServices from '../ProjectServices';
import { AppContext } from '../../../context/AppContext';


class createLabelForm extends React.Component {

    constructor(props) {
        super(props)
        const proj = this.props.project
        this.state = {
            projectName: proj.name,
            labelName : "",
            error: null,
            message: null
        }
    }

    handleSubmit = () => {
        this.setState({ final: true }, this.handleProjectLabelCreate)
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

    async handleProjectLabelCreate () {
        const labelToCreate = this.state
        console.log(labelToCreate)

        if(!labelToCreate.labelName){
            this.setState({error: 'Please insert something'})
            return
        }

        const createProjectLabelResponse = await ProjectServices.createProjectLabel(
            labelToCreate.projectName,
            labelToCreate.labelName,
            this.context.authToken
        )

        const createProjectLabelContent = await createProjectLabelResponse.json()
        
        console.log(createProjectLabelContent)

        if(createProjectLabelContent.status === 200) {
            this.setState({message: `Project Label Created`}, this.handleStateReset)
        } else {
            this.setState({error: createProjectLabelContent.properties.detail})
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
            projectName: '',
            labelName: ''
        })
    }

  render() {
      const {projectName, labelName} = this.state
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
            
            <Form.TextArea name="labelName" value={labelName} label='Project Label' placeholder='Write your label name' onChange={this.handleChange} />
            
            <Form.Button primary icon="save" />
        </Form>
      )
  }

}

createLabelForm.contextType = AppContext
  
export default createLabelForm