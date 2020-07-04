import React from 'react';
import { Form, Message} from 'semantic-ui-react'
import ProjectServices from '../ProjectServices';
import ProjectDataModels from '../ProjectDataModels';
import { AppContext } from '../../../context/AppContext';


class UpdateProjectForm extends React.Component {

    constructor(props) {
        super(props)
        const proj = this.props.project
        this.state = {
            name: proj.name,
            descr : "",
            error: null,
            message: null
        }
    }

    handleSubmit = () => {
        this.setState({ final: true }, this.handleProjectUpdate)
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

    async handleProjectUpdate () {
        const projectToUpdate = this.state
        console.log(projectToUpdate)

        if(!projectToUpdate.descr){
            this.setState({error: 'Please insert something to update'})
            return
        }

        const updateProjectResponse = await ProjectServices.updateProjectOfUser(
            projectToUpdate.name,
            ProjectDataModels.updateIssueDataModel(projectToUpdate.descr),
            this.context.authToken
        )

       

        const updateProjectContent = await updateProjectResponse.json()
        
        console.log("this is the response status "+ updateProjectContent.status)

        if(updateProjectContent.status === 200) {
            this.setState({message: `Project ${updateProjectContent.properties.id} updated`}, this.handleStateReset)
        } else {
            this.setState({error: updateProjectContent.properties.detail})
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
            name: '',
            descr: ''
        })
    }

  render() {
      const {name, descr} = this.state
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
            
            <Form.TextArea name="descr" value={descr} label='Description' placeholder='Write your description' onChange={this.handleChange} />
            
            <Form.Button primary icon="save" />
        </Form>
      )
  }

}
  
UpdateProjectForm.contextType = AppContext

export default UpdateProjectForm