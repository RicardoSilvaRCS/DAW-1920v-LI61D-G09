import React from 'react';
import { Container, Header, Message } from 'semantic-ui-react'
import ProjectServices from '../ProjectServices'
import ProjectsDataModel from '../ProjectDataModels'
/* Components Import*/
import ContentList from '../../../components/ContentList'
import CreateEntityModal from '../../../components/CreateEntityModal'
import CreateProjectForm from '../../../components/CreateProjectForm'

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

    /**
     * Make renders for both non auth and auth pages
     */

    renderAuthUserInfo() {
        return (
            <Container text>      
              <Header as='h1'>Projects</Header>        
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
                  <ContentList members={this.state.projects} icon='clipboard list'/> 
                </Container>      
              }
              <br/>
              <CreateEntityModal entity="Project">
                  <CreateProjectForm />
              </CreateEntityModal>
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
            projects: []
        }
    }


  render() {
    return this.state.auth ? this.renderAuthUserInfo() : this.renderNonAuthInfo()
  }

}

export default ProjectIntro;