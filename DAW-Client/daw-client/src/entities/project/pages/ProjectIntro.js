import React from 'react';
import { Container, Header, Icon, Message, Modal, List, Button } from 'semantic-ui-react'
import ProjectServices from '../ProjectServices'
import ProjectsDataModel from '../ProjectDataModels'
/* Components Import*/
import CreateEntityModal from '../../../components/CreateEntityModal'
import CreateProjectForm from './CreateProjectForm'

class ProjectIntro extends React.Component {

    //For now, since we don't have the auth done, to test each render (auth and nonauth) just change the property "auth" in the state object


    getUserProjects = async () => {
      const getProjOfUserResponse = await ProjectServices.getProjectsOfUser("Joao")
      console.log("Response received on the GetProjects:")
      console.log(getProjOfUserResponse)
      if(getProjOfUserResponse.status === 200) {
        const getProjectsContent = await getProjOfUserResponse.json()
        const projectProps = []
        getProjectsContent.forEach(proj => {
          projectProps.push(proj.properties)
        })
        return projectProps
      } else {
        return []
      }
    }

    async componentDidMount() {
        const projectProps = await this.getUserProjects()
        this.setState({projects: projectProps})
    }

    handleRemoveProject = (e, {name}) => {
      let projToDelete = null
      let idx = 0
      this.setState(state => {
        let projects = state.projects
        for(; idx<projects.length; idx++) {
          if(projects[idx].name === name) {
              projToDelete = projects[idx]
              break;
          }
        }
        return state
      }, async () => {
        console.log(projToDelete)
        if(!projToDelete) {
          this.setState({error: "Project selected doesn't exist. Try again later."})
          return
        }
        let deleteProjectResponse = await ProjectServices.deleteProject(projToDelete.name)
        console.log("Response received on the Delete Project:")
        console.log(deleteProjectResponse)
        //Remember that if the request sends a projectName for a project that doesn't exist, the Server assumes it was deleted w/ sucess, even if the proj doesn't exist
        if(deleteProjectResponse.status === 200) {
          this.setState(state=>{
            let projects = state.projects
            projects.splice(idx, 1)
            state.projects = projects
            state.message = `${projToDelete.name} deleted with sucess.`
            return state
          })
        } else {
          let deleteProjectContent = await deleteProjectResponse.json()
          console.log("Content of Delete Project response:")
          console.log(deleteProjectContent)
          this.setState({error: deleteProjectContent.properties.detail})
        }
        this.setState({delProjModalState: {open: false, projToDelete: ''}})
      })
    }

    handleDeleteModal = (e, {name}) => {
      console.log(name)
      this.setState({delProjModalState: {open: true, projToDelete: name}})
    }

    handleDelProjModalClose = () => {
      this.setState({delProjModalState: {open: false, projToDelete: ''}})
    }



    /**
     * Make renders for both non auth and auth pages
     */

    renderAuthUserInfo() {
      const delProjModalState = this.state.delProjModalState
        return (
            <Container text>      
              <Header as='h1'>Projects</Header>
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
              {this.state.projects.length <= 0 && 
                <Container>
                  <Message info>
                    <Message.Header>No projects started yet.</Message.Header>
                  </Message>
                </Container>
              }
              {this.state.projects.length > 0 &&   
                <Container>
                  <p>Here you can see your collection of projects.
                    Access all the information of your projects, edit or delete them, and add new ones to your collection.
                  </p>
                  <List size='huge' divided>
                    {this.state.projects.map( (it) => (
                      <List.Item key={it.name}>
                      <List.Icon name='clipboard list' size='large' verticalAlign='middle' />
                      <List.Content>
                          <List.Header as='a' href={`/projects/${it.name}/details`}>{it.name}</List.Header>
                          <List.Description as='p'>
                            {it.descr}
                            <Button name={it.name} icon negative style={{float: "right"}} onClick={this.handleDeleteModal}>
                              <Icon name='close' />
                            </Button>
                          </List.Description>   
                      </List.Content>
                      </List.Item>
                    ))}
                  </List>
                </Container>      
              }
              <br/>
              <CreateEntityModal entity="Project">
                  <CreateProjectForm />
              </CreateEntityModal>
              <Modal
              dimmer="blurring" 
              open={delProjModalState.open}
              closeIcon
              style={
                {height: "auto", top: "auto", left: "auto", right: "auto", bottom: "auto"}
              }
              onClose={this.handleDelProjModalClose}
              >
                <Modal.Header>Delete Project {delProjModalState.projToDelete}</Modal.Header>
                <Modal.Content>
                  <p>Are you sure you want to delete project {delProjModalState.projToDelete}</p>
                </Modal.Content>
                <Modal.Actions>
                  <Button negative labelPosition='right' icon='close' content='No' onClick={this.handleDelProjModalClose}/>
                  <Button name={delProjModalState.projToDelete} positive labelPosition='right' icon='checkmark' content='Yes' onClick={this.handleRemoveProject}/>
                </Modal.Actions>
              </Modal>
            </Container>
          )
    }

    renderNonAuthInfo() {
        return (
            <Container text>
              <Header as='h1'>Projects</Header>
              <p>In this area you will be able to create your own projects and then manage them.
                Issues can be associated to each project, and you will be able to comment each
                issue with information you find usefull.
              </p>
              <p>In order to acess this area you need to <a href="/login">login</a> if
               you already have an account, or <a href="/register">register</a> if you don't.</p>
            </Container>
          )
    }

    constructor(props) {
        super(props)
        this.state = {
            auth: true,
            projects: [],
            error: null,
            message: null,
            delProjModalState: {
              open: false,
              projToDelete: ''
            }
        }
    }


  render() {
    return this.state.auth ? this.renderAuthUserInfo() : this.renderNonAuthInfo()
  }

}

export default ProjectIntro;