import React from 'react';
import { Form, Message, Dropdown, Container, Button, Icon } from 'semantic-ui-react'
import IssueServices from '../IssueServices'
import IssueDataModel from '../IssuesDataModel'
import { AppContext } from '../../../context/AppContext';


/**
 * For now it's creating Issues in Proect10 from user Joao, but we need to do the auth "global state" or something to create it on the user that is authenticated
 */
class CreateIssueForm extends React.Component {

    handleSubmit = () => {
        this.setState({ final: true }, this.handleIssueCreation)
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

    handleLabelChange = (e, { name, value }) => {
        this.setState(state => {
            const list = state.labels.map(item => {
                if(item.labelid === name) {
                    item.labelvalue = value
                }
                return item
            })
            state.labels = list
            return state    
        })
    }

    handleAddLabel = (e) => {
        this.setState(state => {
            const list = state.labels.concat({
                labelid: state.labels.length,
                labelvalue: ''
            })
            state.labels = list
            return state
        })
    }

    handleRemoveLabel = (e, {name, value}) => {
        this.setState(state => {
            const labels = state.labels
            let idxToRemove = -1
            let found = false
            for(let i=0; i<labels.length; i++) {
                if(i === name) {
                    idxToRemove = i
                    found = true
                    break;
                }
            }
            if(found) {
                labels.splice(idxToRemove, 1)
                for(let i = idxToRemove; i<labels.length; i++) {
                    labels[i].labelid--
                }
            }
            state.labels = labels
            return state    
        })
    }

    async handleIssueCreation() {
        if(!this.state.final) {
            this.setState({error: 'Unexpected error occured. Sorry for the inconvenience please try again later.'})
            return
        }
        const createIssueResponse = await IssueServices
            .createIssue(IssueDataModel
                .createIssueDataModel(
                    this.state.issuename,
                    this.state.projname,
                    this.state.descr,
                    this.state.currState,
                    this.state.labels
                ),
                this.context.authToken
                )

        const createIssueContent = await createIssueResponse.json()
        
        if(createIssueResponse.status === 200) {
            this.setState({message: `Issue ${createIssueContent.properties.name} created`}, this.handleStateReset)
        } else {
            this.setState({error: createIssueContent.properties.detail})
        }
    }
    
    handleStateReset() {
        this.setState({
            issuename: '',
            projname: '',
            descr: '',
            labels: [{
                labelid: 0,
                labelvalue: ''
            }],
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
        const projDetails = this.props.project
        this.state = {
            issuename: '',
            projname: projDetails.name,
            descr: '',
            currState: projDetails.initstate,
            labels: [{
                labelid: 0,
                labelvalue: ''
            }],
            projDetails: projDetails,
            final: false,
            error: null,
            message: null
        }
    }

  render() {
    const {issuename, projname, descr, currState, labels} = this.state
    const handleLabelChange = this.handleLabelChange
    const handleRemoveLabel = this.handleRemoveLabel

    const projDetails = this.state.projDetails
    const availableLabels = projDetails.labels.map((label)=>{
        return {
            key: label,
            text: label,
            value: label
        }
    })

    const renderLabels = labels.map(function(it){
        return (
            <Form.Group key={`group:${it.labelid}`}>
                <Dropdown placeholder="Select a label" selection key={it.labelid} name={it.labelid} options={availableLabels} onChange={handleLabelChange}/>
                <Button name={it.labelid} icon negative onClick={handleRemoveLabel}>
                    <Icon name='close' />
                </Button>
            </Form.Group>
        )
    })

      return (
        <Container text>
            <Form>
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
                <Form.Input required icon='question' iconPosition='left' label="Issue Name:" placeholder='Name' name='issuename' value={issuename} onChange={this.handleChange}/>
                <Form.Input required icon='keyboard' iconPosition='left' label="Issue Description:" placeholder='Description' name='descr' value={descr} onChange={this.handleChange}/>
                <Form.Input icon='caret right' iconPosition='left' label="Initial State:" content={projDetails.initstate} name='currState' value={projDetails.initstate} disabled/>
                <p>Here you can select from the available labels:</p>
                {renderLabels}
                <Button secondary style={{marginBottom: "10px"}} onClick={this.handleAddLabel}>Add label</Button>
                {!this.state.final && 
                    <Form.Button primary content='Create Issue' onClick={this.handleSubmit}/>
                }
                {this.state.final && 
                    <Form.Button primary loading disabled={true}/>
                }
            </Form>
        </Container>
      )
  }

}
  
CreateIssueForm.contextType = AppContext

export default CreateIssueForm