

import React from 'react';
import { Accordion, Button, Container, Header, Icon, Message, Modal, List, Label } from 'semantic-ui-react'
import { Link } from 'react-router-dom'
import ProjectServices from '../ProjectServices';
import IssuesServices from '../../issue/IssueServices'

/*Components*/

import CreateIssueForm from '../../issue/pages/CreateIssueForm'
import CreateLabelForm from './CreateLabelForm'
import EntityModal from '../../../components/EntityModal'
import UpdateProjectInfo from './UpdateProjectForm'
import ListStatesComponent from '../../../components/ListStates'
import ListLabelsWithDeleteComponent from '../../../components/ListLabelsWithDelete'
import ListTransitionsComponent from '../../../components/ListTransitions'
import { AppContext } from '../../../context/AppContext';


class ProjectDetailed extends React.Component {


    async componentDidMount() {
        const { projName } = this.props.match.params
        this.setState({ projName: projName })
        this.getProjectDetailedInfo(projName)
    }

    async getProjectDetailedInfo(projName) {
        if (!projName) {
            this.setState({ error: "Invalid project name." })
            return
        }

        const getProjDetailsResponse = await ProjectServices.getProjectDetails(projName, this.context.authToken)

        const getProjDetailsContent = await getProjDetailsResponse.json()

        if (getProjDetailsResponse.status === 200) {
            const projInfo = getProjDetailsContent.properties

            const getProjIssuesResponse = await IssuesServices.getProjectIssues(projName, this.context.authToken)

            const getProjIssuesContent = await getProjIssuesResponse.json()

            if (getProjIssuesResponse.status === 200) {
                const projIssues = []
                getProjIssuesContent.forEach(issue => {
                    projIssues.push(issue.properties)
                });

                this.setState({
                    projInfo: {
                        details: projInfo,
                        issues: projIssues
                    }
                })
            } else {
                this.setState({
                    projInfo: {
                        details: projInfo,
                        issues: []
                    }
                })
                return
            }
        } else {
            this.setState({ error: getProjDetailsContent.properties.detail })
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
        const prevState = this.state.accordionState
        this.setState({ accordionState: !prevState })
    }

    handleDeleteModal = (e, { name }) => {
        this.setState({ delIssueModalState: { open: true, issueToDelete: name } })
    }

    handleDelIssueModalClose = () => {
        this.setState({ delIssueModalState: { open: false, issueToDelete: '' } })
    }

    handleRemoveIssue = async (e, { name }) => {
        let issueToDelete = null
        let idx = 0
        let issues = this.state.projInfo.issues
        for (; idx < issues.length; idx++) {
            if (issues[idx].id === name) {
                issueToDelete = issues[idx]
                break;
            }
        }
        console.log(issueToDelete)
        if (!issueToDelete) {
            this.setState({ error: "Issue selected doesn't exist. Try again later." })
            return
        }
        let deleteIssueResponse = await IssuesServices.deleteIssue(issueToDelete.id, this.context.authToken)
        //Remember that if the request sends an id for an Issue that doesn't exist, the Server assumes it was deleted w/ sucess, even if the issue doesn't exist
        if (deleteIssueResponse.status === 200) {
            this.setState(state => {
                let issues = state.projInfo.issues
                issues.splice(idx, 1)
                state.projInfo.issues = issues
                state.message = `Issue ${issueToDelete.name} deleted with sucess.`
                state.delIssueModalState = { open: false, issueToDelete: '' }
                return state
            })
        } else {
            let deleteIssueContent = await deleteIssueResponse.json()
            console.log("[ProjectDetailedPage] Content of Delete Issue response:")
            console.log(deleteIssueContent)
            this.setState({
                error: deleteIssueContent.properties.detail,
                delIssueModalState: { open: false, issueToDelete: '' }
            })
        }
    }


    handleDeleteLabelModal = (e, { labelName }) => {
        this.setState({ delLabelModalState: { open: true, labelToDelete: labelName } })
    }

    handleDeleleteLabelModalClose = () => {
        this.setState({ delLabelModalState: { open: false, labelToDelete: '' } })
    }

    handleRemoveLabel = async (e, { name }) => {
        let labelToDelete = name
        let projectName = this.state.projName

        //ver qual a mensagem de erro
        if (!labelToDelete) {
            this.setState({ error: "Label selected doesn't exist. Try again later." })
            return
        }

        let deleteLabelResponse = await ProjectServices.deleteProjectLabel(projectName, labelToDelete, this.context.authToken)

        if (deleteLabelResponse.status === 200) {
            this.setState(state => {
                let labels = state.projInfo.details.labels
                let idx = 0

                for (; idx < labels.length; idx++) {
                    if (labels[idx] === name) {
                        labelToDelete = labels[idx]
                        break;
                    }
                }

                labels.splice(idx, 1)
                state.projInfo.details.labels = labels

                state.message = `Label ${labelToDelete} deleted with sucess.`
                state.delLabelModalState = { open: false, labelToDelete: '' }
                return state
            })
        } else {
            let deleteLabelContent = await deleteLabelResponse.json()
            this.setState({
                error: deleteLabelContent.properties.detail,
                delLabelModalState: { open: false, labelToDelete: '' }
            })
        }
    }

    renderLabelDeleteModal(delLabelModalState) {
        return (
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
        )
    }

    renderIssueDeleteModal(delIssueModalState) {
        return (
            <Modal
                dimmer="blurring"
                open={delIssueModalState.open}
                closeIcon
                style={
                    { height: "auto", top: "auto", left: "auto", right: "auto", bottom: "auto" }
                }
                onClose={this.handleDelIssueModalClose}
            >
                <Modal.Header>Delete Issue {delIssueModalState.issueToDelete}</Modal.Header>
                <Modal.Content>
                    <p>Are you sure you want to delete issue {delIssueModalState.issueToDelete}?</p>
                </Modal.Content>
                <Modal.Actions>
                    <Button negative labelPosition='right' icon='close' content='No' onClick={this.handleDelIssueModalClose} />
                    <Button name={delIssueModalState.issueToDelete} positive labelPosition='right' icon='checkmark' content='Yes' onClick={this.handleRemoveIssue} />
                </Modal.Actions>
            </Modal>
        )
    }

    renderedProjDetails(projInfo, delLabelModalState) {
        return (
            <List>
                <List.Item>
                    <Label color="blue" horizontal content="Description" icon="keyboard" size="large" />
                    {projInfo.details.descr}
                    <EntityModal entity="Project Info" icon="edit">
                        <UpdateProjectInfo project={projInfo.details} />
                    </EntityModal>
                </List.Item>
                <EntityModal entity="Project Label" icon="plus">
                    <CreateLabelForm project={projInfo.details} />
                </EntityModal>
                <ListLabelsWithDeleteComponent labels={projInfo.details.labels} handlerDelete={this.handleDeleteLabelModal}/>
                
                {this.renderLabelDeleteModal(delLabelModalState)}

                <List.Item>
                    <Label color="blue" horizontal content="Initial State" icon="play" size="large" />
                    {projInfo.details.initstate}
                </List.Item>

                <ListStatesComponent states={projInfo.details.states} />
                <ListTransitionsComponent transitions={projInfo.details.transitions} />
            </List>
        )
    }

    renderedProjIssues(projInfo) {
        return (
            <List size='large' divided>
                {projInfo.issues.map((it) => (
                    <List.Item key={it.name}>
                        <List.Icon name='file' size='big' verticalAlign='middle' />
                        <List.Content>
                            <List.Header style={{ fontSize: "20px" }}>
                                <Link to={`/issues/${it.id}/details`}>{it.name}</Link>
                            </List.Header>    
                            <List.Description>
                                {it.descr}
                                <Button name={it.id} icon="delete" basic style={{ float: "right" }} onClick={this.handleDeleteModal}></Button>
                            </List.Description>
                            <List.Description>
                                Current State: {it.state}
                            </List.Description>
                        </List.Content>
                    </List.Item>
                ))}
            </List>
        )
    }

    renderAuthUserInfo() {
        const { projName, projInfo, accordionState, delIssueModalState, delLabelModalState } = this.state

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
                <Accordion fluid>
                    <Accordion.Title icon='dropdown' content={`Issues of ${projName}:`} style={{ fontSize: "25px" }} onClick={this.handleAcccordionClick} />
                    <Accordion.Content active={accordionState}>
                        {projInfo.issues.length <= 0 &&
                            <Container>
                                <EntityModal entity="Issue" icon="plus" floated="left" size="big">
                                    <CreateIssueForm project={projInfo.details} />
                                </EntityModal>
                                <br></br><br></br>
                                <Message warning>
                                    <Message.Header>No issues started yet.</Message.Header>
                                </Message> 
                            </Container>
                        }
                        {projInfo.issues.length > 0 &&
                            <Container>
                                <EntityModal entity="Issue" icon="plus" floated="left" size="big">
                                    <CreateIssueForm project={projInfo.details} />
                                </EntityModal>
                                <br/>
                                <br/>
                                
                                {this.renderedProjIssues(projInfo)}

                                {this.renderIssueDeleteModal(delIssueModalState)}

                                <br/>
                                <br/>
                                <br/>
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
            message: null
        }
    }


    render() {
        return this.context.isAuth ? this.renderAuthUserInfo() : this.renderNonAuthInfo()
    }

}

ProjectDetailed.contextType = AppContext

export default ProjectDetailed;