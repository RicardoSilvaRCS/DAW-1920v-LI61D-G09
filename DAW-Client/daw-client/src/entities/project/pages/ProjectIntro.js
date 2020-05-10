import React from 'react';
import { Container, Header } from 'semantic-ui-react'
import ProjectServices from '../ProjectServices'

class ProjectIntro extends React.Component {


    //For now, since we don't have the auth done, to test each render (auth and nonauth) just change the property "auth" in the state object

    checkUserAuth = async () => {
        /**
         * TODO: Check if user is auth, if we have the user info, we need to request 
         *       the projects for the user logged in.
         *       Here we need to change the state property "auth" to select which component to show    
         */

        
        //If a user is auth, we will change the property "auth", and then request the projects of that user to present
        const getProjOfUserResponse = await ProjectServices.getProjectsOfUser("Joao") 
        console.log(getProjOfUserResponse)
        
        //console.log(`[ProjectIntro] Received body: ${JSON.stringify(body)}`)

        /*
         * TODO: If a user is auth change the state.auth to true or false
         */
    }

    componentDidMount() {
        this.checkUserAuth()
    }

    /**
     * Make renders for both non auth and auth pages
     */

    renderAuthUserInfo() {
        return (
            <Container text>
              <Header as='h1'>TODO: Present authenticated user projects</Header>
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
            auth: false
        }
    }

  render() {
    return this.state.auth ? this.renderAuthUserInfo() : this.renderNonAuthInfo()
  }

}

export default ProjectIntro;