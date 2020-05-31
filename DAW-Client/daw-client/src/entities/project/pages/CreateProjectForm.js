import React from 'react';
import { Form, Message, Container, Button, Icon } from 'semantic-ui-react'
import ProjectServices  from '../ProjectServices'
import ProjectDataModels from '../ProjectDataModels'
import {AppContext} from '../../../context/AppContext'


/**
 * For now it's creating project in user Joao, but we need to do the auth "global state" or something to create it on the user that is authenticated
 */
class CreateProjectForm extends React.Component {
    
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
        const createProjectResponse = await ProjectServices
            .createProjectOfUser(this.context.authUserName, 
                                ProjectDataModels.projectCreationDataModel(
                                    this.state.projname,
                                    this.state.descr,
                                    this.state.labels,
                                    this.state.initstate,
                                    this.state.transitions
                                ))
        console.log("[CreateProjectForm] Response received on the Project Creation Request:")
        console.log(createProjectResponse)
        const createProjectContent = await createProjectResponse.json()
        console.log("[CreateProjectForm] Content of Project Creation response:")
        console.log(createProjectContent)
        if(createProjectResponse.status === 200) {
            this.setState({message: `Project ${createProjectContent.properties.name} created`}, this.handleStateReset)
        } else {
            this.setState({error: createProjectContent.properties.detail})
        }
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

    handleTransitionChange = (e, { name, value }) => {
        let id_name = name.split(":")
        let transid = parseInt(id_name[0])
        let transtype = id_name[1]
        console.log(id_name)
        this.setState(state => {
            const list = state.transitions.map(item => {
                console.log(item)
                if(item.transid === transid) {
                    item[transtype]  = value
                }
                return item
            })
            state.transitions = list
            return state
        })
    }

    handleAddTransition = (e) => {
        this.setState(state => {
            const list = state.transitions
            list.splice(list.length-1, 0, {
                starttrans: '',
                endtrans: '',
                transid: list.length-1,
                final: false
            })
            list[list.length-1].transid = list.length-1
            state.transitions = list
            return state
        })
    }

    handleRemoveTransition = (e, {name, value}) => {
        let id = parseInt(name.split(":")[0])
        this.setState(state => {
            const transitions = state.transitions
            let idxToRemove = -1
            let found = false
            for(let i=0; i<transitions.length; i++) {
                if(i === id) {
                    idxToRemove = i
                    found = true
                    break;
                }
            }
            if(found) {
                transitions.splice(idxToRemove, 1)
                for(let i = idxToRemove; i<transitions.length; i++) {
                    transitions[i].transid--
                }
            }
            state.transitions = transitions
            return state    
        })
    }

    handleStateReset() {
        this.setState({
            projname: '',
            descr: '',
            labels: [{
                labelid: 0,
                labelvalue: ''
            }],
            initstate: '',
            transitions:  [
                {
                    starttrans: '',
                    endtrans: '',
                    transid: 0,
                    final: false
                },
                {
                starttrans: 'Closed',
                endtrans: 'Archived',
                transid: 1,
                final: true
                }
             ],
             final: false
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
        this.state = {
            projname: '',
            descr: '',
            labels: [{
                labelid: 0,
                labelvalue: ''
            }],
            initstate: '',
            transitions: [
                {
                    starttrans: '',
                    endtrans: '',
                    transid: 0,
                    final: false
                },
                {
                starttrans: 'Closed',
                endtrans: 'Archived',
                transid: 1,
                final: true
                }
             ],
            final: false,
            error: null,
            message: null
        }
    }

  render() {
    const {projname, descr, labels, initstate, transitions} = this.state
    const handleLabelChange = this.handleLabelChange
    const handleTransitionChange = this.handleTransitionChange
    const handleRemoveLabel = this.handleRemoveLabel
    const handleRemoveTransition = this.handleRemoveTransition

    const renderLabels = this.state.labels.map(function(it){
        return (
            <Form.Group key={`group:${it.labelid}`}>
                <Form.Input icon='tag' iconPosition='left' placeholder='Label' key={it.labelid} name={it.labelid} value={it.labelvalue} onChange={handleLabelChange}></Form.Input>
                <Button name={it.labelid} icon negative onClick={handleRemoveLabel}>
                    <Icon name='close' />
                </Button>
            </Form.Group>
        )
    })

    const renderedTransitions = this.state.transitions.map(function(it){
        return (
            <Form.Group key= {it.transid}>
                <Form.Input icon='caret right' iconPosition='left' placeholder='Start Transition' name={`${it.transid}:starttrans`} value={it.starttrans} onChange={handleTransitionChange} disabled={it.final}></Form.Input>
                <Icon disabled name="arrow right" size="big"></Icon>
                <Form.Input icon='caret right' iconPosition='left' placeholder='End Transition' name={`${it.transid}:endtrans`} value={it.endtrans} onChange={handleTransitionChange} disabled={it.final}></Form.Input>
                <Button name={`${it.transid}:deletetransbutton`} icon negative disabled={it.final} onClick={handleRemoveTransition}>
                    <Icon name='close' />
                </Button>
            </Form.Group>
        )
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
                <Form.Input required icon='user' iconPosition='left' label="Project Name:"placeholder='Name' name='projname' value={projname} onChange={this.handleChange}/>
                <Form.Input required icon='keyboard' iconPosition='left' label="Project Description:" placeholder='Description' name='descr' value={descr} onChange={this.handleChange}/>
                <p>Choose labels for your project, you can't repeat labels. The labels choosen here will the ones available for all the issues in this project:</p>
                {renderLabels}
                <Button secondary style={{marginBottom: "10px"}} onClick={this.handleAddLabel}>Add label</Button>
                <p>You can now choose the states your project will have. First choose the initial state and then choose the states, and transitions, you feel are needed.
                    Remember that all the states will end with a transition "Closed -> Archived" soo make sure your transitions reach the state Closed at least one time:</p>     
                <Form.Input required icon='caret right' iconPosition='left' placeholder='Initial State:' name='initstate' value={initstate} onChange={this.handleChange}/>
                {renderedTransitions}
                <Button secondary style={{marginBottom: "10px"}} onClick={this.handleAddTransition}>Add transition</Button>
                <Form.Button primary content='Create Project' />
            </Form>
        </Container>
      )
  }

}
  
CreateProjectForm.contextType = AppContext

export default CreateProjectForm