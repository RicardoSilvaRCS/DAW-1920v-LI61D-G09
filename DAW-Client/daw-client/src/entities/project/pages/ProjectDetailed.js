

import React from 'react';
import { Accordion, Button, Container, Header, Icon, Message, Modal, List,Label } from 'semantic-ui-react'
import ProjectServices from '../ProjectServices';
import IssuesServices from '../../issue/IssueServices'

/*Components*/

import CreateEntityModal from '../../../components/CreateEntityModal'
import CreateIssueForm from '../../issue/pages/CreateIssueForm'
import CreateLabelForm from './CreateLabelForm'
import EntityModal from '../../../components/EntityModal'
import UpdateProjectInfo from './UpdateProjectForm'
import ListStatesComponent from '../../../components/ListStates'
import ListLabelsComponent from '../../../components/ListLabels'
import ListTransitionsComponent from '../../../components/ListTransitions'


class ProjectDetailed extends React.Component {


    async componentDidMount() {
        const {projName} = this.props.match.params
        this.setState({projName: projName})
        this.getProjectDetailedInfo(projName)
    }

    async getProjectDetailedInfo(projName) {
        if(!projName) {
            this.setState({error: "Invalid project name."})
            return
        }

        const getProjDetailsResponse = await ProjectServices.getProjectDetails(projName)
        console.log("[ProjectDetailedPage] Response received on Project Details Request:")
        console.log(getProjDetailsResponse)

        const getProjDetailsContent = await getProjDetailsResponse.json()
        console.log("[ProjectDetailedPage] Content of Get Project Details response:")
        console.log(getProjDetailsContent)

        if(getProjDetailsResponse.status === 200) {
            const projInfo = getProjDetailsContent.properties

            const getProjIssuesResponse = await IssuesServices.getProjectIssues(projName)
            console.log("[ProjectDetailedPage] Response received on Project Issues Request:")
            console.log(getProjIssuesResponse)

            const getProjIssuesContent = await getProjIssuesResponse.json()
            console.log("[ProjectDetailedPage] Content of Get Project Issues response:")
            console.log(getProjIssuesContent)

            if(getProjIssuesResponse.status === 200) {
                const projIssues = []
                getProjIssuesContent.forEach(issue => {
                    projIssues.push(issue.properties)
                });

                this.setState({projInfo: {
                    details: projInfo,
                    issues: projIssues
                }})
            } else {
                this.setState({projInfo: {
                    details: projInfo,
                    issues: []
                }})
                return
            }
        } else {
            this.setState({error: getProjDetailsContent.properties.detail})
            return
        }

    }

    handleDismissMessage = () => {
        this.setState({ message: null })
    }

    handleDismissError = () => {
        this.setState({ error: null })
    }

    handleAcccordionClick = () => {
        const prevState  = this.state.accordionState
        this.setState({ accordionState: !prevState})
    }

    handleDeleteModal = (e, {name}) => {
        this.setState({delIssueModalState: {open: true, issueToDelete: name}})
    }

    handleDelIssueModalClose = () => {
        this.setState({delIssueModalState: {open: false, issueToDelete: ''}})
    }

    handleRemoveIssue = async (e, {name}) => {
        let issueToDelete = null
        let idx = 0
        let issues = this.state.projInfo.issues
        for(; idx<issues.length; idx++) {
            if(issues[idx].id === name) {
                issueToDelete = issues[idx]
                break;
            }
        }
        console.log(issueToDelete)
        if(!issueToDelete) {
            this.setState({error: "Issue selected doesn't exist. Try again later."})
            return
        }
        let deleteIssueResponse = await IssuesServices.deleteIssue(issueToDelete.id)
        console.log("[ProjectDetailedPage] Response received on the Delete Issue Request:")
        console.log(deleteIssueResponse)
        //Remember that if the request sends an id for an Issue that doesn't exist, the Server assumes it was deleted w/ sucess, even if the issue doesn't exist
        if(deleteIssueResponse.status === 200) {
            this.setState(state=>{
                let issues = state.projInfo.issues
                issues.splice(idx, 1)
                state.projInfo.issues = issues
                state.message = `Issue ${issueToDelete.name} deleted with sucess.`
                state.delIssueModalState = {open: false, issueToDelete: ''}
                return state
            })
        } else {
            let deleteIssueContent = await deleteIssueResponse.json()
            console.log("[ProjectDetailedPage] Content of Delete Issue response:")
            console.log(deleteIssueContent)
            this.setState({
                error: deleteIssueContent.properties.detail,
                delIssueModalState: {open: false, issueToDelete: ''}
            })
        }
    }

    
    handleDeleteLabelModal = (e, {name}) => {
        this.setState({delLabelModalState: {open: true, labelToDelete: name}})
    }

    handleDeleleteLabelModalClose = () => {
        this.setState({delLabelModalState: {open: false, labelToDelete: ''}})
    }

    handleRemoveLabel = async (e, {name}) => {
        let labelToDelete = name
        let projectName = this.state.projName
        
        console.log(labelToDelete)

        //ver qual a mensagem de erro
        if(!labelToDelete) {
            this.setState({error: "Label selected doesn't exist. Try again later."})
            return
        }

        let deleteLabelResponse = await ProjectServices.deleteProjectLabel(projectName, labelToDelete)

        console.log("[ProjectDetailedPage] Response received on the Delete Page Request:")
        console.log(deleteLabelResponse)

        if(deleteLabelResponse.status === 200) {
            this.setState(state=>{
                let labels = state.projInfo.details.labels
                let idx = 0

                for (; idx < labels.length; idx++) {
                    if (labels[idx] === name) {
                        labelToDelete = labels[idx]
                        break;
                    }
                }

                labels.splice(idx, 1)
                state.projInfo.details.labels= labels

                state.message = `Label ${labelToDelete} deleted with sucess.`
                state.delLabelModalState = {open: false, labelToDelete: ''}
                return state
            })
        } else {
            let deleteLabelContent = await deleteLabelResponse.json()
            console.log("[ProjectDetailedPage] Content of Delete Label response:")
            console.log(deleteLabelContent)
            this.setState({
                error: deleteLabelContent.properties.detail,
                delLabelModalState: {open: false, labelToDelete: ''}
            })
        }
    }




    renderedProjDetails(projInfo, delLabelModalState) {
        console.log(projInfo)
        return (
            <List>
                <List.Item>
                    <List.Icon name="keyboard"/>
                    <List.Content>
                        <List.Header>Description:</List.Header>
                        <List.Description>{projInfo.details.descr}</List.Description>
                    </List.Content>
                </List.Item>

                <List.Item>
                    <List.Icon name="tags" />
                    <List.Content>
                        <List.Header>Labels:</List.Header>
                        {projInfo.details.labels.length <= 0 && (
                            <List.Description>No labels.</List.Description>
                        )}
                        {projInfo.details.labels.length > 0 && (
                            <List.List>
                                {projInfo.details.labels.map((label) => (
                                    <List.Item key={`${label}`}>
                                        <List.Icon name='dot circle' size='tiny' verticalAlign='middle' />
                                        <List.Content>
                                            <List.Description>
                                                {label}
                                                <Button name={label} icon negative style={{ float: "right"}} onClick={this.handleDeleteLabelModal}>
                                                    <Icon name='close' />
                                                </Button>
                                            </List.Description>
                                            
                                        </List.Content>
                                    </List.Item>
                                ))}
                            </List.List>
                        )}
                    </List.Content>
                </List.Item>

                <Modal
                    dimmer="blurring"
                    open={delLabelModalState.open}
                    closeIcon
                    style={
                        { height: "auto", top: "auto", left: "auto", right: "auto", bottom: "auto" }
                    }
                    onClose={this.handleDeleleteLabelModalClose}
                >
                    <Modal.Header>Delete Label {delLabelModalState.labelToDelete}</Modal.Header>
                    <Modal.Content>
                        <p>Are you sure you want to delete label {delLabelModalState.labelToDelete}?</p>
                    </Modal.Content>
                    <Modal.Actions>
                        <Button negative labelPosition='right' icon='close' content='No' onClick={this.handleDeleleteLabelModalClose} />
                        <Button name={delLabelModalState.labelToDelete} positive labelPosition='right' icon='checkmark' content='Yes' onClick={this.handleRemoveLabel} />
                    </Modal.Actions>
                </Modal>                       

                <CreateEntityModal entity="Project Label">
                    <CreateLabelForm project={projInfo.details} />
                </CreateEntityModal>
                <List.Item>
                    <Label color="blue" horizontal content="Initial State" icon="play" size="large"/>
                    {projInfo.details.initstate}
                </List.Item>
                <ListStatesComponent states={projInfo.details.states}/>
                <ListTransitionsComponent transitions={projInfo.details.transitions}/>
            </List>
        )
    }

    renderedProjIssues(projInfo) {
        return (
            <List size='large' divided>
                {projInfo.issues.map( (it) => (
                <List.Item key={it.name}>
                <List.Icon name='file' size='big' verticalAlign='middle' />
                <List.Content>
                    <List.Header as='a' style={{fontSize: "20px"}} href={`/issues/${it.id}/details`}>{it.name}</List.Header>
                    <List.Description>
                        {it.descr}
                    </List.Description>  
                    <List.Description>
                        Current State: {it.state}
                        <Button name={it.id} icon negative style={{float: "right"}} onClick={this.handleDeleteModal}>
                            <Icon name='close' />
                        </Button>
                    </List.Description> 
                </List.Content>
                </List.Item>
                ))}
            </List>
        )
    }

    renderAuthUserInfo() {
        const {projName, projInfo, accordionState, delIssueModalState, delLabelModalState} = this.state
        

        return (           
            <Container text>
                <Header as='h1'>Project {projName} details</Header>
                <p>In this area you will be able to see the detailed information of {projName}.
                    It's properties and issues, you will also be able to edit it, delete it and add more issues. 
                </p>

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
                <Header as='h2'>Details:</Header>
                {projInfo.details && (this.renderedProjDetails(projInfo, delLabelModalState))}
                <EntityModal entity="Project Info">
                    <UpdateProjectInfo project={projInfo.details} />
                </EntityModal>
                <Accordion fluid>
                    <Accordion.Title icon='dropdown' content={`Issues of ${projName}:`} style={{fontSize:"25px"}} onClick={this.handleAcccordionClick}/>
                    <Accordion.Content active={accordionState}>
                    {projInfo.issues.length <= 0 && 
                        <Container>
                        <Message warning>
                            <Message.Header>No issues started yet.</Message.Header>
                        </Message>
                        <br></br>
                            <CreateEntityModal entity="Issue">
                                <CreateIssueForm project={projInfo.details}/>
                            </CreateEntityModal>
                        <br></br>
                        </Container>
                    }
                    {projInfo.issues.length > 0 &&   
                        <Container>
                            <p>Here you can see {projName} issues.
                                Click on them to see their details.
                            </p>
                            <br></br>
                            <CreateEntityModal entity="Issue">
                                <CreateIssueForm project={projInfo.details}/>
                            </CreateEntityModal>
                            <br></br>
                            {this.renderedProjIssues(projInfo)}
                            <Modal
                                dimmer="blurring" 
                                open={delIssueModalState.open}
                                closeIcon
                                style={
                                    {height: "auto", top: "auto", left: "auto", right: "auto", bottom: "auto"}
                                }
                                onClose={this.handleDelIssueModalClose}
                                >
                                <Modal.Header>Delete Issue {delIssueModalState.issueToDelete}</Modal.Header>
                                <Modal.Content>
                                    <p>Are you sure you want to delete issue {delIssueModalState.issueToDelete}?</p>
                                </Modal.Content>
                                <Modal.Actions>
                                    <Button negative labelPosition='right' icon='close' content='No' onClick={this.handleDelIssueModalClose}/>
                                    <Button name={delIssueModalState.issueToDelete} positive labelPosition='right' icon='checkmark' content='Yes' onClick={this.handleRemoveIssue}/>
                                </Modal.Actions>
                            </Modal>
                        </Container>      
                    }
                    </Accordion.Content>
                </Accordion>
            </Container>
          )
    }

    renderNonAuthInfo() {
        return (
            <Container text>
              <Header as='h1'>Project Information</Header>
              <p>In this area you will be able to see the detailed information of a specific project.
                 It's properties and issues, you will also be able to edit it, delete it and add more issues. 
              </p>
              <p>In order to acess this area you need to <a href="/login">login</a> if
               you already have an account, or <a href="/register">register</a> if you don't.</p>
            </Container>
          )
    }


    constructor(props) {
        super(props)
        this.state = {
            projName: '',
            projInfo: {
                details: null,
                issues: []
            },
            accordionState: false,
            delIssueModalState: {
                open: false,
                issueToDelete: ''
            },
            delLabelModalState: {
                open: false,
                labelToDelete: ''
            },
            error: null,
            message: null,
            auth: true
        }
    }


  render() {
    return this.state.auth ? this.renderAuthUserInfo() : this.renderNonAuthInfo()
  }

}

export default ProjectDetailed;